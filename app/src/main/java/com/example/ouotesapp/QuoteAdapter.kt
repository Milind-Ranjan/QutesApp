package com.example.ouotesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class QuoteAdapter(private val quoteList: List<Quote>) : RecyclerView.Adapter<QuoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_item, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quoteList[position]
        holder.quotesTv.text = quote.quote
        holder.authorTv.text = quote.author
    }

    override fun getItemCount(): Int {
        return quoteList.size
    }
}