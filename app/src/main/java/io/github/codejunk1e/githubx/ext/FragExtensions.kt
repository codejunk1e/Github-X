package io.github.codejunk1e.githubx.ext

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigateTo(destination: Int){
    findNavController().navigate(destination)
}