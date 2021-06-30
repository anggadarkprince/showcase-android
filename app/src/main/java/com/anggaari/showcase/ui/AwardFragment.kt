package com.anggaari.showcase.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggaari.showcase.R
import com.anggaari.showcase.adapters.AwardAdapter
import com.anggaari.showcase.databinding.FragmentAwardBinding
import com.anggaari.showcase.utils.MarginItemDecoration
import com.anggaari.showcase.utils.NetworkResult
import com.anggaari.showcase.viewmodels.AwardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AwardFragment : Fragment() {
    private var _binding: FragmentAwardBinding? = null
    private val binding get() = _binding!!

    private lateinit var awardViewModel: AwardViewModel
    private val adapter by lazy { AwardAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        awardViewModel = ViewModelProvider(requireActivity()).get(AwardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAwardBinding.inflate(inflater, container, false)

        setupRecyclerView()

        lifecycleScope.launchWhenStarted {
            requestApiData()
        }

        return binding.root;
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.defaultSpace
                )
            )
        )
        showShimmerEffect()
    }

    private fun requestApiData() {
        Log.d("AwardFragment", "requestApiData() called")
        awardViewModel.getAwards(emptyMap())
        awardViewModel.awardsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    Log.d("AwardFragment", "requestApiData() success")
                    hideShimmerEffect()
                    response.data?.let { adapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    Log.d("AwardFragment", "requestApiData() error: " + response.message.toString())
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("AwardFragment", "requestApiData() loading")
                    showShimmerEffect()
                }
            }
        })
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.recyclerview.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}