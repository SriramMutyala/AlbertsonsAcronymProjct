package com.albertsons.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albertsons.assignment.data.network.Result
import com.albertsons.assignment.data.model.Dictionary
import com.albertsons.assignment.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: Repository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    ViewModel() {
    private var _results = MutableLiveData<Result<List<Dictionary>>>()

    fun getMeanings(enteredText: String) {
        viewModelScope.launch(dispatcher) {
            _results.postValue(Result.loading())
            val response = repository.getMeanings(
                enteredText
            )
            _results.postValue(response)
        }
    }

    fun results() = _results

}
