package com.riztech.githubapp.presentation.detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.riztech.githubapp.databinding.FragmentDetailBinding
import com.riztech.githubapp.domain.model.Games.Games
import com.riztech.githubapp.presentation.util.Result
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    private fun updateDetailUser(game: Games){
        binding.tvLogin.text = game.name
        Glide.with(this).load(game.backgroundImage).into(binding.ivBackground)
        binding.tvLogin.text = game.name
        binding.ivRating.text = game.rating.toString()
        binding.tvAdded.text = game.added.toString()
        binding.tvDesc.text = Html.fromHtml(game.description)
        Glide.with(binding.root).load(game.backgroundImage).into(binding.ivAvatar)
        if (game.isFavorite ?: false){
            binding.textButton.text = "Remove From Favorite"
        }else{
            binding.textButton.text = "Add To Favorite"
        }
        binding.btnFav.setOnClickListener {
            if (game.isFavorite ?: false){
                viewModel.removeLocalUser(game)
            }else{
                viewModel.saveLocalUser(game)
            }
            findNavController().popBackStack()

        }
    }

    private fun subscribeData() {
        viewModel.detail.observe(viewLifecycleOwner, { result ->
            when(result){
                is Result.Success -> {
                    updateDetailUser(result.value)
                }
                is Result.Failure -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}