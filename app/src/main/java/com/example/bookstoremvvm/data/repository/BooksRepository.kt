package com.example.bookstoremvvm.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.bookstoremvvm.data.model.Book
import com.example.bookstoremvvm.data.model.GetBookDetailResponse
import com.example.bookstoremvvm.data.model.GetBooksResponse
import com.example.bookstoremvvm.data.source.remote.BookService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksRepository(private val bookService: BookService) {

    val booksLiveData = MutableLiveData<List<Book>?>()
    val errorMessageLiveData = MutableLiveData<String>()
    val booksDetailLiveData = MutableLiveData<Book?>()
    val loadingLiveData = MutableLiveData<Boolean>()

    fun getBooks() {
        loadingLiveData.value = true
        bookService.getProducts().enqueue(object : Callback<GetBooksResponse> {
            override fun onResponse(call: Call<GetBooksResponse>, response: Response<GetBooksResponse>) {
                val result = response.body()?.books

                if (result.isNullOrEmpty().not()) {
                    booksLiveData.value = result
                } else {
                    booksLiveData.value = null
                }

                loadingLiveData.value = false
            }

            override fun onFailure(call: Call<GetBooksResponse>, t: Throwable) {
                errorMessageLiveData.value = t.message.orEmpty()
                loadingLiveData.value = false
                Log.e("GetProducts", t.message.orEmpty())
            }
        })
    }

    fun getBooksDetail(id:Int) {
        loadingLiveData.value = true

        bookService.getBookDetail(id).enqueue(object :
            Callback<GetBookDetailResponse> {
            override fun onResponse(
                call: Call<GetBookDetailResponse>,
                response: Response<GetBookDetailResponse>
            ) {
                val result = response.body()?.book

                if (result != null) {
                    booksDetailLiveData.value = response.body()?.book
                } else {
                    booksDetailLiveData.value = null
                }

                loadingLiveData.value = false
            }

            override fun onFailure(call: Call<GetBookDetailResponse>, t: Throwable) {
                errorMessageLiveData.value = t.message.orEmpty()
                loadingLiveData.value = false
                Log.e("GetBooks", t.message.orEmpty())
            }
        })
    }
}