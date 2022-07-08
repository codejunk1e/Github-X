package io.github.codejunk1e.githubx.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.github.codejunk1e.githubx.databinding.FragmentUsersBinding
import io.github.codejunk1e.githubx.ext.hide
import io.github.codejunk1e.githubx.ext.show
import io.github.codejunk1e.githubx.repositories.RepositoriesAdapter
import io.github.codejunk1e.githubx.repositories.RepositoriesViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UsersAdapter
    private val viewModel: UsersViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UsersAdapter {
            val action = UsersFragmentDirections.actionUsersToUserprofileFragment(it)
            findNavController().navigate(action)
        }
        binding.recycler.adapter = adapter
        observeViewModel()
        setOnClickListeners()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (!state.isLoading) binding.searchProgress.hide() else binding.searchProgress.show()

                    if (state.users.isEmpty()
                        && binding.userSearch.text?.isEmpty() == true
                        && !state.isLoading
                    ) binding.searchForUsers.show()
                    else binding.searchForUsers.hide()

                    if (state.users.isEmpty()
                        && binding.userSearch.text?.isNotEmpty() == true
                        && !state.isLoading
                    ) binding.usersNotFound.show()
                    else binding.usersNotFound.hide()

                    state.error?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                        viewModel.errorShown()
                    }
                    adapter.submitList(state.users)
                }
            }
        }
    }

    private fun setOnClickListeners(){
        binding.apply {
            searchButton.setOnClickListener {
                viewModel.searchUser(binding.userSearch.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}