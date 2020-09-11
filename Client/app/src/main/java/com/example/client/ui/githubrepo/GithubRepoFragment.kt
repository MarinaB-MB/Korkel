package com.example.client.ui.githubrepo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.R
import com.example.client.ui.detail.DetailRepoActivity
import com.example.client.utils.DataState
import com.example.client.utils.makeGone
import com.example.client.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_github_repo.*
import kotlinx.android.synthetic.main.fragment_repos.ivError
import kotlinx.android.synthetic.main.fragment_repos.pvLoad
import kotlinx.android.synthetic.main.fragment_repos.rvRepos

@AndroidEntryPoint
class GithubRepoFragment : Fragment(R.layout.fragment_github_repo) {

    private val viewModel: GithubRepoViewModel by viewModels()
    private lateinit var adapter: GithubReposAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initObserver() {
        viewModel.githubRepos.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    pvLoad.makeVisible()
                    rvRepos.makeGone()
                    ivError.makeGone()
                }
                is DataState.Error -> {
                    it.exception.printStackTrace()
                    ivError.makeVisible()
                    pvLoad.makeGone()
                    rvRepos.makeGone()
                }
                is DataState.Success -> {
                    ivError.makeGone()
                    pvLoad.makeGone()
                    rvRepos.makeVisible()
                    adapter.setData(it.data)
                }
            }
        })
    }

    private fun initView() {
        swrLayoutGithub.setOnRefreshListener {
            Handler().postDelayed({
                swrLayoutGithub.isRefreshing = false
                viewModel.getGithubRepos()
            }, 1500)
        }
        rvRepos.layoutManager = LinearLayoutManager(context)
        adapter = GithubReposAdapter(listOf(), object : GithubReposAdapter.OnRepoClickListener {
            override fun onRepoClick(fullName: String) {
                showDetailActivity(fullName)
            }
        })
        rvRepos.adapter = adapter
    }

    private fun showDetailActivity(fullName: String) {
        val intent = Intent(context, DetailRepoActivity::class.java).apply {
            putExtra(DetailRepoActivity.FULL_NAME, fullName)
        }
        startActivity(intent)
    }
}