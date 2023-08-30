package com.example.bookstoremvvm.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bookstoremvvm.R
import com.example.bookstoremvvm.common.viewBinding
import com.example.bookstoremvvm.data.model.Book
import com.example.bookstoremvvm.databinding.FragmentBooksBinding
import com.example.bookstoremvvm.ui.home.adapter.BooksAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BooksFragment : Fragment(R.layout.fragment_books), BooksAdapter.BookListener {

    private val binding by viewBinding(FragmentBooksBinding::bind)

    private val booksAdapter by lazy { BooksAdapter(this) }

    private val viewModel by viewModels<BooksViewModel>()

    var bestSellerBook : Book? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvBooks.adapter = booksAdapter
            ivBestSellerBook.setOnClickListener {
                val action = bestSellerBook?.id?.let { it ->
                    BooksFragmentDirections.actionBooksFragmentToDetailFragment(it)
                }
                if (action != null) {
                    findNavController().navigate(action)
                }
            }
        }

        viewModel.getBooks()

        observeData()
    }

    private fun observeData() {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        viewModel.booksLiveData.observe(viewLifecycleOwner) { list ->
            if(list != null) {
                booksAdapter.submitList(list)
                bestSellerBook = list?.firstOrNull { it.bestSeller }

                bestSellerBook?.id
                bestSellerBook?.let {
                    Glide.with(requireContext())
                        .load(it.imageUrl)
                        .into(binding.ivBestSellerBook)
                }
            } else {
                Snackbar.make(requireView(), "Empty List", 1000).show()
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, 1000).show()
        }
    }

    override fun onBookClick(id: Int) {
        val action = BooksFragmentDirections.actionBooksFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }
}