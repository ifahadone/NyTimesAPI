package com.fahad.sicpa.activities.popular

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahad.sicpa.adapters.ArticleAdapter
import com.fahad.sicpa.databinding.ActivityPopularBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.parcelize.Parcelize

@Parcelize
enum class PopularArticleType : Parcelable {
    MOST_VIEWED,
    MOST_SHARED,
    MOST_EMAILED
}

@AndroidEntryPoint
class PopularActivity : AppCompatActivity() {

    companion object {
        private const val POPULAR_TYPE = "popular_type"

        fun newIntent(context: Context, type: PopularArticleType): Intent {
            return Intent(context, PopularActivity::class.java).apply {
                this.putExtra(POPULAR_TYPE, type.name)
            }
        }
    }

    private lateinit var binding: ActivityPopularBinding
    private lateinit var type: PopularArticleType

    private val articleAdapter by lazy { ArticleAdapter() }

    private val viewModel: PopularViewModel by lazy {
        ViewModelProvider(this).get(PopularViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityPopularBinding.inflate(layoutInflater).run {
                binding = this
                this.root
            }
        )

        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach { state -> handlePopularState(state) }
            .launchIn(lifecycleScope)

        getTypeFromIntent()
        setupViews()
        setupListeners()

        viewModel.getPopularArticles(type)
    }

    private fun handlePopularState(state: PopularState) {
        when (state) {
            is PopularState.Init -> Unit

            is PopularState.LoadingPopularArticle -> {
                binding.recyclerViewArticle.isGone = true
                binding.progressIndicator.isVisible = true
            }

            is PopularState.GetPopularArticleSuccess -> {
                binding.recyclerViewArticle.isVisible = true
                binding.progressIndicator.isGone = true

                articleAdapter.setArticles(state.data)
            }

            is PopularState.GetPopularArticleFailed -> {
                Toast.makeText(this, state.error.message, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun getTypeFromIntent() {
        type = intent?.getStringExtra(POPULAR_TYPE)?.run {
            PopularArticleType.valueOf(this)
        } ?: PopularArticleType.MOST_VIEWED
    }

    private fun setupViews() {
        setToolbarTitle()
        setupRecyclerView()
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setToolbarTitle() {
        binding.toolbar.apply {
            this.title = when (type) {
                PopularArticleType.MOST_VIEWED -> "Most Viewed"
                PopularArticleType.MOST_SHARED -> "Most Shared"
                PopularArticleType.MOST_EMAILED -> "Most Emailed"
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewArticle.apply {
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = articleAdapter
        }
    }
}