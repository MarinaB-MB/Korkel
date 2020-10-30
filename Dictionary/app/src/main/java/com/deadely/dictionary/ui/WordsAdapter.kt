package com.deadely.dictionary.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deadely.dictionary.R
import com.deadely.dictionary.dataclass.Word
import kotlinx.android.synthetic.main.word_item.view.*

class WordsAdapter(private var dataList: List<Word>) :
    RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder =
        WordsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        )

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    inner class WordsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(word: Word) {
            with(itemView) {
                tvWord.text = word.word
                tvDescription.text = word.description
            }
        }
    }
}
