package com.example.client.ui.repos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.model.RepositoryModel
import kotlinx.android.synthetic.main.row_repository.view.*


class ReposAdapter(
    private var repoList: List<RepositoryModel>,
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

    fun setData(list: List<RepositoryModel>) {
        repoList = list
        notifyDataSetChanged()
    }

    interface OnRepoClickListener {
        fun onRepoClick(fullName: String)
    }
}
