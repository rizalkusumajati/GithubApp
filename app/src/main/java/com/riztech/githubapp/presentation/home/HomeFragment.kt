package com.riztech.githubapp.presentation.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.riztech.githubapp.R
import com.riztech.githubapp.databinding.FragmentHomeBinding
import com.riztech.githubapp.presentation.favorite.FavoriteFragment
import com.riztech.githubapp.presentation.popular.PopularFragment
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {
    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding get() = _binding!!

    companion object {
        fun newInstance() = HomeFragment()
    }

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.apply {
            adapter = FragmentAdapter(requireActivity())
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
            tab.text = if (position == 0)  "Popular" else "Favorite"
        }.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class FragmentAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> PopularFragment.newInstance()
                1 -> FavoriteFragment.newInstance()
                else -> PopularFragment.newInstance()
            }
        }

    }
}