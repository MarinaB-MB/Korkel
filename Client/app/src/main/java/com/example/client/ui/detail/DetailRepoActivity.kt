package com.example.client.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.client.R
import com.example.client.model.RepoDetail
import com.example.client.utils.DataState
import com.example.client.utils.makeGone
import com.example.client.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail_repo.*

@AndroidEntryPoint
class DetailRepoActivity : AppCompatActivity(R.layout.activity_detail_repo) {

    private val viewModel: DetailRepoViewModel by viewModels()
    private lateinit var adapter: CommitAdapter

    companion object {
        const val FULL_NAME = "full_name"
    }

    private var fulName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar!!
        actionBar.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        getData()
        initObserver()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getData() {
        fulName = intent?.extras?.getString(FULL_NAME)
        viewModel.getRepo(fulName!!)
        viewModel.getCommits(fulName!!)
    }


    private fun initObserver() {
        viewModel.repoDetail.observe(this, {
            when (it) {
                is DataState.Loading -> {
                    pvDetailLoad.makeVisible()
                    ivDetailError.makeGone()
                    llContent.makeGone()
                }
                is DataState.Error -> {
                    it.exception.printStackTrace()
                    ivDetailError.makeVisible()
                    pvDetailLoad.makeGone()
                    llContent.makeGone()
                }
                is DataState.Success -> {
                    llContent.makeVisible()
                    ivDetailError.makeGone()
                    pvDetailLoad.makeGone()
                    initView(it.data)
                }
            }
        })
        viewModel.commitsRepo.observe(this, {
            when (it) {
                is DataState.Loading -> {
                    pvDetailLoad.makeVisible()
                    ivDetailError.makeGone()
                    llContent.makeGone()
                }
                is DataState.Error -> {
                    it.exception.printStackTrace()
                    ivDetailError.makeVisible()
                    pvDetailLoad.makeGone()
                    llContent.makeGone()
                }
                is DataState.Success -> {
                    llContent.makeVisible()
                    ivDetailError.makeGone()
                    pvDetailLoad.makeGone()
                    adapter.setData(it.data)
                }
            }
        })
    }

    private fun initView(repoDetail: RepoDetail) {
        repoDetail.apply {
            tvName.text = name
            tvAuthor.text = owner.login
            Glide.with(baseContext)
                .load(owner.avatar_url)
                .centerCrop()
                .circleCrop()
                .into(ivAuthor)
            tvDescription.text = description
            tvLanguages.text = language
            tvForkCount.text = forks.toString()
            tvStarsCount.text = stargazers_count.toString()
        }
        rvCommit.layoutManager = LinearLayoutManager(this)
        adapter = CommitAdapter(listOf())
        rvCommit.adapter = adapter
    }
}
