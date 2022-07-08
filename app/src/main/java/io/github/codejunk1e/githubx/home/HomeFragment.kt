package io.github.codejunk1e.githubx.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import io.github.codejunk1e.githubx.R
import io.github.codejunk1e.githubx.databinding.FragmentHomeBinding
import io.github.codejunk1e.githubx.ext.navigateTo

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            usersCard.setOnClickListener { navigateTo(R.id.users) }
            reposCard.setOnClickListener { navigateTo(R.id.repositories) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}