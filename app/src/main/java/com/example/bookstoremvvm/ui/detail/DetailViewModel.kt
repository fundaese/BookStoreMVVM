package com.example.bookstoremvvm.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookstoremvvm.data.model.Book
import com.example.bookstoremvvm.data.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val booksRepository: BooksRepository): ViewModel() {

    private var _booksDetailLiveData = MutableLiveData<Book?>()
    val booksDetailLiveData: LiveData<Book?>
        get() = _booksDetailLiveData

    private var _errorMessageLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
        get() = _errorMessageLiveData

    private var _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    init {
        _booksDetailLiveData = booksRepository.booksDetailLiveData
        _errorMessageLiveData = booksRepository.errorMessageLiveData
        _loadingLiveData = booksRepository.loadingLiveData
    }

    fun getBooksDetail(id: Int) {
        booksRepository.getBooksDetail(id)
    }
}