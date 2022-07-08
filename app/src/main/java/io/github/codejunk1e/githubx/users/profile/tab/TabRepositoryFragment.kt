package io.github.codejunk1e.githubx.users.profile.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.github.codejunk1e.githubx.databinding.FragmentTabRepositoryBinding
import io.github.codejunk1e.githubx.ext.hide
import io.github.codejunk1e.githubx.ext.show
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TabRepositoryFragment : Fragment() {
    private lateinit var adapter: TabFragAdapter
    private val viewModel: TabViewModel by activityViewModels()
    private var _binding: FragmentTabRepositoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabRepositoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(KEY) }?.apply {
            val login: String = getString(KEY).toString()
            viewModel.getUsersRepos(login)
        }
        adapter = TabFragAdapter()
        binding.recycler.adapter = adapter
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->

                    state.error?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                        viewModel.errorShown()
                    }

                    adapter.submitList(state.repos)
                    if (state.repos.isEmpty() && !state.isLoading) binding.reposNotFound.show()
                    else binding.reposNotFound.hide()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}