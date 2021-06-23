package com.narcyber.mvpbasics.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.narcyber.mvpbasics.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment() {
    lateinit var root: FragmentFavouriteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return FragmentFavouriteBinding.inflate(inflater, container, false).root
    }

}