package com.example.client.ui.githubrepo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.R
import com.example.client.ui.detail.DetailRepoActivity
import com.example.client.ui.repos.ReposFragment
import com.example.client.ui.repos.SwipeHelper
import com.example.client.ui.repos.UnderlayButton
import com.example.client.ui.repos.UnderlayButtonClickListener
import com.example.client.utils.DataState
import com.example.client.utils.PageListener
import com.example.client.utils.makeGone
import com.example.client.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_github_repo.*
import kotlinx.android.synthetic.main.fragment_github_repo.llHeader
import kotlinx.android.synthetic.main.fragment_repos.*
import kotlinx.android.synthetic.main.fragment_repos.ivError
import kotlinx.android.synthetic.main.fragment_repos.pvLoad
import kotlinx.android.synthetic.main.fragment_repos.rvRepos

@AndroidEntryPoint
class GithubRepoFragment : Fragment(R.layout.fragment_github_repo), PageListener {

    private val viewModel: GithubRepoViewModel by viewModels()
    private lateinit var adapter: GithubReposAdapter
    private var status = ""

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
                    llHeader.makeGone()
                }
                is DataState.Error -> {
                    it.exception.printStackTrace()
                    ivError.makeVisible()
                    pvLoad.makeGone()
                    llHeader.makeGone()
                    rvRepos.makeGone()
                }
                is DataState.Success -> {
                    ivError.makeGone()
                    pvLoad.makeGone()
                    llHeader.makeVisible()
                    rvRepos.makeVisible()
                    adapter.setData(it.data)
                }
            }
        })
        viewModel.repoDetail.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading,
                is DataState.Error -> {
                }
                is DataState.Success -> {
                    if (status == ReposFragment.ADD) viewModel.addToDb(it.data)
                    else viewModel.deleteFromDb(it.data)
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
        val llm = LinearLayoutManager(context)
        rvRepos.layoutManager = llm
        adapter = GithubReposAdapter(listOf(), object : GithubReposAdapter.OnRepoClickListener {
            override fun onRepoClick(fullName: String) {
                showDetailActivity(fullName)
            }
        })
        rvRepos.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(rvRepos) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = delete(position)
                val addButton = add(position)
                buttons = listOf(deleteButton, addButton)
                return buttons
            }
        })

        itemTouchHelper.attachToRecyclerView(rvRepos)
        com.example.client.utils.PaginationUtils.initPagination(
            rvRepos,
            llm,
            this
        )
    }


    private fun add(position: Int): UnderlayButton {
        return UnderlayButton(
            requireContext(),
            "Добавить",
            14.0f,
            android.R.color.holo_green_light,
            object : UnderlayButtonClickListener {
                override fun onClick() {
                    status = ReposFragment.ADD
                    getName(position)
                }
            })
    }

    private fun delete(position: Int): UnderlayButton {
        return UnderlayButton(
            requireContext(),
            "Удалить",
            14.0f,
            android.R.color.holo_red_light,
            object : UnderlayButtonClickListener {
                override fun onClick() {
                    status = ReposFragment.DELETE
                    getName(position)
                }
            })
    }

    private fun getName(position: Int) {
        viewModel.getRepoDetail(adapter.getElement(position).full_name)
    }


    private fun showDetailActivity(fullName: String) {
        val intent = Intent(context, DetailRepoActivity::class.java).apply {
            putExtra(DetailRepoActivity.FULL_NAME, fullName)
        }
        startActivity(intent)
    }

    override fun onPagination(page: Int) {
        viewModel.getGithubRepos(page)
    }
}