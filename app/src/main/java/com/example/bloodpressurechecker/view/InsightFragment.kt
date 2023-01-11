package com.example.bloodpressurechecker.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bloodpressurechecker.R
import com.example.bloodpressurechecker.databinding.FragmentInsightBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsightFragment : Fragment() {
    private var _binding: FragmentInsightBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentInsightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = binding.bottomNavigationInsights
        val navHostFragment: NavHostFragment = childFragmentManager
            .findFragmentById(R.id.nav_host_fragment_insights) as NavHostFragment

        navController = navHostFragment.findNavController()
        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}