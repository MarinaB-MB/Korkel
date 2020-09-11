package com.example.client.ui.favorite

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
import com.example.client.utils.makeGone
import com.example.client.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_repos.*

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorites) {
    private val viewModel: FavoriteViewModel by viewModels()

    private var status = ""
    private lateinit var adapter: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }

    private fun initObserver() {
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
    }

    private fun initView() {
        swrLayout.setOnRefreshListener {
            Handler().postDelayed({
                swrLayout.isRefreshing = false
                viewModel.getFavoritesRepos()
            }, 1500)
        }
        rvRepos.layoutManager = LinearLayoutManager(context)
        adapter =
            FavoritesAdapter(mutableListOf(), object : FavoritesAdapter.OnRepoClickListener {
                override fun onRepoClick(fullName: String) {
                    openDetailScreen(fullName)
                }

            })
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(rvRepos) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                var buttons = listOf<UnderlayButton>()
                val deleteButton = delete(position)
                val addButton = add(position)
                buttons = listOf(deleteButton, addButton)
                return buttons
            }
        })
        rvRepos.adapter = adapter
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
                    status = ReposFragment.ADD
                    viewModel.addToDB(adapter.getElement(position))
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
                    viewModel.deleteFromDB(adapter.getElement(position))
                    viewModel.getFavoritesRepos()
                }
            })
    }

    private fun openDetailScreen(fullName: String) {
        val intent = Intent(context, DetailRepoActivity::class.java).apply {
            putExtra(DetailRepoActivity.FULL_NAME, fullName)
        }
        startActivity(intent)
    }
}