package com.example.hht_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hht_app.data.model.BSM
import com.example.hht_app.data.repository.BaggageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaggageViewModel @Inject constructor(
    private val repository: BaggageRepository
) : ViewModel() {

    sealed class ProcessResult {
        data class Success(val bsm: BSM) : ProcessResult()
        data class Error(val message: String) : ProcessResult()
    }


    private val _bsmList = MutableStateFlow<List<BSM>>(emptyList())
    val bsmList: StateFlow<List<BSM>> = _bsmList.asStateFlow()

    // 處理結果狀態
    private val _processResult = MutableStateFlow<ProcessResult?>(null)
    val processResult: StateFlow<ProcessResult?> = _processResult.asStateFlow()

    init {
        loadBSMList()
        //dev only
        //delete all data for development
        deleteAllBSM()
        insertDummyData()
    }

    private fun loadBSMList() {
        viewModelScope.launch {
            repository.getBSMList().collect { bsmList ->
                _bsmList.value = bsmList
            }
        }
    }

    fun processBaggage(tagNumber: String) {
        viewModelScope.launch {
            repository.processBaggage(tagNumber).fold(
                onSuccess = { bsm ->
                    _processResult.value = ProcessResult.Success(bsm)
                },
                onFailure = { error ->
                    _processResult.value = ProcessResult.Error(error.message ?: "未知錯誤")
                }
            )
        }
    }

    // 清除處理結果
    fun clearProcessResult() {
        _processResult.value = null
    }

    private fun insertDummyData() {
        viewModelScope.launch {
            repository.insertDummyData()
        }
    }

    //delete all data for development
    fun deleteAllBSM() {
        viewModelScope.launch {
            repository.deleteAllBSM()
        }
    }
}