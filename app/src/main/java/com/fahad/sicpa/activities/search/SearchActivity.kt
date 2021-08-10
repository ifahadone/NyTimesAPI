package com.fahad.sicpa.activities.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahad.sicpa.adapters.ArticleAdapter
import com.fahad.sicpa.databinding.ActivitySearchBinding
import com.fahad.sicpa.util.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    private lateinit var binding: ActivitySearchBinding

    private val articleAdapter by lazy { ArticleAdapter() }

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivitySearchBinding.inflate(layoutInflater).run {
                binding = this
                this.root
            }
        )

        viewModel.state
            .flowWithLifecycle(lifecycle)
            .onEach { state -> handleSearchState(state) }
            .launchIn(lifecycleScope)

        setupViews()
        setupListeners()
    }

    private fun handleSearchState(state: SearchState) {
        when (state) {
            is SearchState.Init -> {
                binding.progressIndicator.isGone = true
            }

            is SearchState.LoadingSearchArticle -> {
                binding.recyclerViewArticle.isGone = true
                binding.progressIndicator.isVisible = true
            }

            is SearchState.GetSearchArticleSuccess -> {
                binding.recyclerViewArticle.isVisible = true
                binding.progressIndicator.isGone = true

                articleAdapter.setArticles(state.data)
            }

            is SearchState.GetSearchArticleFailed -> {
                Toast.makeText(this, state.error.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun setupViews() {
        showKeyboardForEditText()
        setupRecyclerView()
    }

    @FlowPreview
    private fun setupListeners() {
        binding.imageViewBack.setOnClickListener {
            finish()
        }

        lifecycleScope.launch {
            binding.editTextSearch.textChanges()
                .debounce(300)
                .distinctUntilChanged()
                .flowOn(Dispatchers.Default)
                .collect { text ->
                    if (text.isNotBlank()) {
                        viewModel.searchArticle(text.toString())
                    }
                }
        }
    }

    private fun showKeyboardForEditText() {
        binding.editTextSearch.apply {
            this.requestFocus()
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewArticle.apply {
            this.layoutManager = LinearLayoutManager(this.context)
            this.adapter = articleAdapter
        }
    }
}