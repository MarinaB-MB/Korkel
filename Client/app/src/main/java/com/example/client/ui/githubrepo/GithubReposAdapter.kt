package com.example.client.ui.githubrepo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.model.GithubRepo
import kotlinx.android.synthetic.main.row_repository.view.*


class GithubReposAdapter(
    private var repoList: MutableList<GithubRepo>,
    val clickListener: OnRepoClickListener? = null
) :
    RecyclerView.Adapter<GithubReposAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_repository, parent, false)
        )

    override fun getItemCount(): Int = repoList.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(repoList[position])
    }


    inner class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repoDetail: GithubRepo) {
            with(itemView) {
                tvName.text = repoDetail.name
                tvAuthor.text = repoDetail.owner.login
                itemView.setOnClickListener { clickListener?.onRepoClick(repoDetail.full_name) }
            }
        }
    }

    fun getElement(position: Int): GithubRepo {
        return repoList[position]
    }

    fun addList(list: List<GithubRepo>) {
        repoList.addAll(list)
        notifyDataSetChanged()
    }

    interface OnRepoClickListener {
        fun onRepoClick(fullName: String)
    }
}
