package com.example.stenograffia.ui.route

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ModelFactory(val id: String): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == RouteViewModel::class.java) {
            RouteViewModel(id) as T
        } else null!!
    }

}