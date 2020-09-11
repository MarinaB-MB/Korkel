package com.example.client.ui.repos

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
import com.example.client.ui.detail.DetailRepoActivity.Companion.FULL_NAME
import com.example.client.utils.DataState
import com.example.client.utils.makeGone
import com.example.client.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repos.*


@AndroidEntryPoint
class ReposFragment() : Fragment(R.layout.fragment_repos) {
    companion object {
        const val ADD = "add"
        const val DELETE = "delete"
    }

    private val viewModel: ReposViewModel by viewModels()
    private var status = ""
    private lateinit var adapter: ReposAdapter

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
                    adapter.addItems(it.data)
                }
            }
        })
        viewModel.repoDetail.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading,
                is DataState.Error -> {
                }
                is DataState.Success -> {
                    if (status == ADD) viewModel.addToDb(it.data)
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

        val llm = LinearLayoutManager(context)
        rvRepos.layoutManager = llm
        adapter = ReposAdapter(mutableListOf(), object : ReposAdapter.OnRepoClickListener {
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
    }

    private fun add(position: Int): UnderlayButton {
        return UnderlayButton(
            requireContext(),
            "Добавить",
            14.0f,
            android.R.color.holo_green_light,
            object : UnderlayButtonClickListener {
                override fun onClick() {
                    status = ADD
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
                    status = DELETE
                    getName(position)
                }
            })
    }

    private fun getName(position: Int) {
        viewModel.getRepoDetail(adapter.getElement(position).full_name)
    }

    private fun showDetailActivity(fullName: String) {
        val intent = Intent(context, DetailRepoActivity::class.java).apply {
            putExtra(FULL_NAME, fullName)
        }
        startActivity(intent)
    }

}