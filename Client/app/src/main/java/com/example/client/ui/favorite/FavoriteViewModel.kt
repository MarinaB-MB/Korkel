package com.example.client.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.client.repository.Repository

class FavoriteViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {
    init {
        getFavoritesRepos()
    }

    private fun getFavoritesRepos() {
        repository.getFavoritesRepos()
    }
}