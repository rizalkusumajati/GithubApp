package com.riztech.githubapp.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.riztech.githubapp.databinding.FragmentFavoriteBinding
import com.riztech.githubapp.domain.model.Games.Games
import com.riztech.githubapp.presentation.home.HomeFragmentDirections
import com.riztech.githubapp.presentation.util.LocalUserAdapter
import com.riztech.githubapp.presentation.util.Result
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class FavoriteFragment : DaggerFragment() {
    private var _binding: FragmentFavoriteBinding? = null
    val binding: FragmentFavoriteBinding get() = _binding!!

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    @Inject
    lateinit var viewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
    }

    private fun updateListUser(users: List<Games>){
        binding.progressBar.isVisible = false
        binding.rvUser.isVisible = true
        binding.rvUser.apply {
            adapter = LocalUserAdapter(users){ user ->
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    login = user?.id ?: 0
                )
                NavHostFragment.findNavController(this@FavoriteFragment).navigate(action)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllLocalData()
    }

    private fun setupObserver() {
        viewModel.localUser.observe(viewLifecycleOwner, { result ->
            when(result){
                is Result.Success -> {
                    updateListUser(result.value)
                }
                is Result.Failure -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                    updateListUser(emptyList())
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