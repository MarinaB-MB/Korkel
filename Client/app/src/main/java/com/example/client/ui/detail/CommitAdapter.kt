package com.example.client.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.client.R
import com.example.client.model.Commits
import com.example.client.utils.formatWithPattern
import kotlinx.android.synthetic.main.row_commit.view.*

class CommitAdapter(
    private var list: List<Commits>
) :
    RecyclerView.Adapter<CommitAdapter.CommitsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitsViewHolder =
        CommitsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_commit, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CommitsViewHolder, position: Int) {
        holder.bind(list[position])
    }


    inner class CommitsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(commitDet: Commits) {
            with(itemView) {
                tvDescription.text = commitDet.commit.message
                tvDate.text = formatWithPattern(commitDet.commit.author.date)
                tvAuthor.text = commitDet.committer?.login ?: "Unknown"
                Glide.with(context)
                    .load(commitDet.committer?.avatar_url)
                    .centerCrop()
                    .error(R.drawable.ic_baseline_person_24)
                    .circleCrop()
                    .into(ivAuthor)
            }
        }
    }


    fun setData(list: List<Commits>) {
        this.list = list
        notifyDataSetChanged()
    }
}

