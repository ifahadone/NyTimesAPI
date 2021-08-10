package com.fahad.sicpa.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fahad.sicpa.R
import com.fahad.sicpa.activities.popular.PopularActivity
import com.fahad.sicpa.activities.popular.PopularArticleType
import com.fahad.sicpa.activities.search.SearchActivity
import com.fahad.sicpa.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ActivityMainBinding.inflate(layoutInflater).run {
                binding = this
                this.root
            }
        )

        setupListeners()
    }

    private fun setupListeners() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_action_search -> {
                    startActivity(SearchActivity.newIntent(this))
                    true
                }

                else -> false
            }
        }

        binding.textViewMostViewed.setOnClickListener {
            startActivity(PopularActivity.newIntent(this, PopularArticleType.MOST_VIEWED))
        }

        binding.textViewMostShared.setOnClickListener {
            startActivity(PopularActivity.newIntent(this, PopularArticleType.MOST_SHARED))
        }

        binding.textViewMostEmailed.setOnClickListener {
            startActivity(PopularActivity.newIntent(this, PopularArticleType.MOST_EMAILED))
        }
    }
}