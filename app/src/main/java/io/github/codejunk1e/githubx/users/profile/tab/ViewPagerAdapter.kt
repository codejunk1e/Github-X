package io.github.codejunk1e.githubx.users.profile.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

const val KEY = "io.github.codejunk1e.githubx.users.profile.tab.KEY"

class ViewPagerAdapter(fragment: Fragment, val user: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        val fragment = TabRepositoryFragment()
        fragment.arguments = Bundle().apply {
            putString(KEY, user)
        }
        return fragment
    }
}
