package io.github.codejunk1e.githubx.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.github.codejunk1e.githubx.databinding.FragmentRepositoriesBinding
import io.github.codejunk1e.githubx.ext.hide
import io.github.codejunk1e.githubx.ext.show
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RepositoriesFragment : Fragment() {

    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RepositoriesAdapter
    private val viewModel: RepositoriesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RepositoriesAdapter()
        binding.recycler.adapter = adapter
        observeViewModel()
        setOnClickListeners()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (!state.isLoading) binding.searchProgress.hide() else binding.searchProgress.show()

                    if (state.repositories.isEmpty()
                        && binding.repositorySearch.text?.isEmpty() == true
                        && !state.isLoading
                    ) binding.searchForRepos.show()
                    else binding.searchForRepos.hide()

                    if (state.repositories.isEmpty()
                        && binding.repositorySearch.text?.isNotEmpty() == true
                        && !state.isLoading
                    ) binding.reposNotFound.show()
                    else binding.reposNotFound.hide()

                    state.error?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                        viewModel.errorShown()
                    }
                    adapter.submitList(state.repositories)
                }
            }
        }
    }

    private fun setOnClickListeners(){
        binding.apply {
            Timber.e("Clicked!")
            searchButton.setOnClickListener {
                viewModel.searchRepo(binding.repositorySearch.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

