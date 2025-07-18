package com.jaseem.githubexplorer.ui.listscreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaseem.githubexplorer.api.Resource
import com.jaseem.githubexplorer.data.listscreen.ListScreenRepository
import kotlinx.coroutines.launch

class ListScreenViewModel(
    private val listScreenRepository: ListScreenRepository
): ViewModel() {

    init {
        viewModelScope.launch {
           when (val response = listScreenRepository.getAllUsers()) {
                is Resource.Error<*> -> {
                    Log.d("DebooogRess", "Error: $response")
                }

                is Resource.Failure<*> -> {
                    Log.d("DebooogRess", "Failure: $response")
                }

                is Resource.Success<*> -> {
                    Log.d("DebooogRess", "Success: $response")
                }
            }
        }
    }
}