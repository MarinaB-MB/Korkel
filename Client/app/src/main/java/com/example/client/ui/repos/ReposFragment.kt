package com.example.client.ui.repos

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.R
import com.example.client.ui.detail.DetailRepoActivity
import com.example.client.ui.detail.DetailRepoActivity.Companion.FULL_NAME
import com.example.client.utils.DataState
import com.example.client.utils.makeGone
import com.example.client.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repos.*

@AndroidEntryPoint
class ReposFragment : Fragment(R.layout.fragment_repos) {
    private val viewModel: ReposViewModel by viewModels()

    private lateinit var adapter: ReposAdapter
    private var status = " "

    companion object {
        const val ADD = "add"
        const val DELETE = "delete"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initSubscribe()
    }

    private fun initSubscribe() {
        viewModel.repos.observe(viewLifecycleOwner, {
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
                    rvRepos.makeGone()
                    llHeader.makeGone()
                }
                is DataState.Success -> {
                    ivError.makeGone()
                    llHeader.makeVisible()
                    pvLoad.makeGone()
                    rvRepos.makeVisible()
                    adapter.setData(it.data)
                }
            }
        })
        viewModel.repoDetail.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    pvLoad.makeVisible()
                    rvRepos.makeGone()
                    ivError.makeGone()
                    llHeader.makeGone()
                }
                is DataState.Error -> {
                    ivError.makeVisible()
                    pvLoad.makeGone()
                    rvRepos.makeGone()
                    llHeader.makeGone()
                }
                is DataState.Success -> {
                    ivError.makeGone()
                    llHeader.makeVisible()
                    pvLoad.makeGone()
                    rvRepos.makeVisible()
                    if (status == ADD)
                        viewModel.addToDB(it.data)
                    else viewModel.deleteFromDb(it.data)
                }
            }
        })


    }

    private fun initView() {
        swrLayout.setOnRefreshListener {
            Handler().postDelayed({
                swrLayout.isRefreshing = false
                viewModel.getRepos()
            }, 1500)
        }
        rvRepos.layoutManager = LinearLayoutManager(context)
        adapter = ReposAdapter(listOf(), object : ReposAdapter.OnRepoClickListener {
            override fun onRepoClick(fullName: String) {
                showDetailActivity(fullName)
            }
        }, object : ReposAdapter.OnFavoriteEventListener {
            override fun addToFavorite(fullName: String) {
                status = ADD
                viewModel.getRepo(fullName)
            }

            override fun deleteFromFavorite(fullName: String) {
                status = DELETE
                viewModel.getRepo(fullName)
            }

        })
        rvRepos.adapter = adapter
    }

    private fun showDetailActivity(fullName: String) {
        val intent = Intent(context, DetailRepoActivity::class.java).apply {
            putExtra(FULL_NAME, fullName)
        }
        startActivity(intent)
    }
}