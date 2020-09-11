package com.example.client.ui.repos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.model.RepositoryModel
import kotlinx.android.synthetic.main.row_repository.view.*


class ReposAdapter(
    private var repoList: MutableList<RepositoryModel>,
    val clickListener: OnRepoClickListener? = null,
) :
    RecyclerView.Adapter<ReposAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_repository, parent, false)
        )

    override fun getItemCount(): Int = repoList.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repoList[position])
    }


    inner class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repoDetail: RepositoryModel) {
            with(itemView) {
                tvName.text = repoDetail.name
                tvAuthor.text = repoDetail.owner.login
                itemView.setOnClickListener { clickListener?.onRepoClick(repoDetail.full_name) }
            }
        }
    }

    fun addItems(data: List<RepositoryModel>) {
        repoList.addAll(data)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        repoList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getElement(position: Int): RepositoryModel {
        return repoList[position]
    }

    interface OnRepoClickListener {
        fun onRepoClick(fullName: String)
    }
}
