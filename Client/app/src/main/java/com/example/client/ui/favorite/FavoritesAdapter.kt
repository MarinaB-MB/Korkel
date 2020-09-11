package com.example.client.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.model.RepoDetail
import kotlinx.android.synthetic.main.row_repository.view.*


class FavoritesAdapter(
    private var repoList: List<RepoDetail>,
    val clickListener: OnRepoClickListener? = null,
) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_repository, parent, false)
        )

    override fun getItemCount(): Int = repoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(repoList[position])
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repoDetail: RepoDetail) {
            with(itemView) {
                tvName.text = repoDetail.name
                tvAuthor.text = repoDetail.owner.login
                itemView.setOnClickListener { clickListener?.onRepoClick(repoDetail.full_name) }
            }
        }
    }

    fun getElement(position: Int): RepoDetail {
        return repoList[position]
    }

    fun setData(list: List<RepoDetail>) {
        repoList = list
        notifyDataSetChanged()
    }

    interface OnRepoClickListener {
        fun onRepoClick(fullName: String)
    }
}
