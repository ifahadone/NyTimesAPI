package com.fahad.sicpa.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fahad.sicpa.databinding.ViewArticleBinding
import com.fahad.sicpa.models.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val articles = arrayListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ViewArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.setArticle(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    fun setArticles(articles: List<Article>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(
        private val binding: ViewArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setArticle(article: Article) {
            binding.textViewTitle.text = article.title
            binding.textViewPublishedDate.text = article.toReadableDate()
        }
    }
}