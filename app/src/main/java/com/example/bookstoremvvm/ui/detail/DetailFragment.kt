package com.example.bookstoremvvm.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.bookstoremvvm.R
import com.example.bookstoremvvm.common.loadImage
import com.example.bookstoremvvm.common.viewBinding
import com.example.bookstoremvvm.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getBooksDetail(args.id)

        observeData()
    }

    private fun observeData()  = with(binding) {
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.detailProgressBar.isVisible = it
        }

        viewModel.booksDetailLiveData.observe(viewLifecycleOwner) { book ->
            if (book != null) {
                tvName.text = book.name
                tvAuthor.text = "Yazar: ${book.author}"
                tvPublisher.text = "Yayımcı: ${book.publisher}"
                tvPrice.text = "Fiyat: ${book.price} ₺"
                ivBook.loadImage(book.imageUrl)
            } else {
                Snackbar.make(requireView(), "Book Not Found", 1000).show()
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, 1000).show()
        }
    }
}