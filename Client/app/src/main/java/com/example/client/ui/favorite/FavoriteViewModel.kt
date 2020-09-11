package com.example.client.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.client.repository.Repository
import kotlinx.coroutines.launch

class FavoriteViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {
    init {
        getFavoritesRepos()
    }

    private fun getFavoritesRepos() {
        viewModelScope.launch {
            repository.getFavoritesRepos()
        }
    }
}