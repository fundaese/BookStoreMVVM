package com.example.bookstoremvvm.data.model

data class GetBooksResponse (
    val code: Int?,
    val message: String?,
    val books: List<Book>?
)