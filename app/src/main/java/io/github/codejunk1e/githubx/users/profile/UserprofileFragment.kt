package io.github.codejunk1e.githubx.users.profile

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
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import io.github.codejunk1e.githubx.customviews.BadgeView
import io.github.codejunk1e.githubx.databinding.FragmentUserprofileBinding
import io.github.codejunk1e.githubx.ext.hide
import io.github.codejunk1e.githubx.ext.loadImage
import io.github.codejunk1e.githubx.ext.show
import io.github.codejunk1e.githubx.users.profile.tab.TabViewModel
import io.github.codejunk1e.githubx.users.profile.tab.ViewPagerAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserprofileFragment : Fragment() {

    private var _binding: FragmentUserprofileBinding? = null
    private val binding get() = _binding!!
    val args: UserprofileFragmentArgs by navArgs()
    private val viewModel: UserProfileViewModel by viewModels()
    private val tabVM: TabViewModel by activityViewModels()
    private lateinit var adapter: ViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserprofileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (!state.isLoading) binding.searchProgress.hide() else binding.searchProgress.show()

                    state.error?.let {
                        Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                        viewModel.errorShown()
                    }
                    state.user?.apply {
                        this.image?.let {
                            binding.avatar.loadImage(it)
                            binding.avatar.show()

                        }
                        binding.name.text = this.name
                        binding.userName.text = this.userName
                        this.bio?.let { binding.bio.text = it }
                        this.location?.let {
                            binding.location.text = it
                            binding.location.show()
                        }
                        this.link?.let {
                            binding.link.text = it
                            binding.link.show()
                        }
                        binding.followers.text = "${this.followers} followers"
                        binding.followers.show()

                        binding.following.text = "${this.following} following"
                        binding.following.show()

                        adapter = ViewPagerAdapter(this@UserprofileFragment, this.login)
                        binding.viewPager.adapter = adapter
                        binding.tabLayout.clipToPadding = false
                        binding.tabLayout.clipChildren = false
                        binding.viewPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER;
                        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, _ ->
                            val badgeView = BadgeView(context)
                            badgeView.setCount(this.repos)
                            tab.customView = badgeView
                        }.attach()
                    }
                }
            }
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                tabVM.state.collect{ state ->
                    if (!state.isLoading) binding.searchProgress.hide() else binding.searchProgress.show()
                }
            }
        }
    }

    private fun setUp() {
        viewModel.getUser(args.userId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}