package com.example.ouotesapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val quotesTv: TextView = itemView.findViewById(R.id.quote_tv)
    val authorTv: TextView = itemView.findViewById(R.id.author_tv)
}