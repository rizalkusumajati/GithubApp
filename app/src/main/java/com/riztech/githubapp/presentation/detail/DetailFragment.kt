package com.riztech.githubapp.presentation.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.riztech.githubapp.R
import com.riztech.githubapp.databinding.FragmentDetailBinding
import com.riztech.githubapp.domain.model.User
import com.riztech.githubapp.presentation.util.Result
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailFragment : DaggerFragment() {
    private var _binding: FragmentDetailBinding? = null
    val binding: FragmentDetailBinding get() = _binding!!

    val args: DetailFragmentArgs by navArgs()
    companion object {
        fun newInstance() = DetailFragment()
    }

    @Inject
    lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserDetail(args.login)

        subscribeData()
    }

    private fun updateDetailUser(user: User){
        binding.tvLogin.text = user.login
        binding.btnFav.setOnClickListener {
            viewModel.saveLocalUser(user)
        }
    }

    private fun subscribeData() {
        viewModel.detail.observe(viewLifecycleOwner, { result ->
            when(result){
                is Result.Success -> {
                    updateDetailUser(result.value)
                }
                is Result.Failure -> {

                }
                is Result.Loading -> {

                }
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}