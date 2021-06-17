package com.narcyber.mvpbasics.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.narcyber.mvpbasics.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    lateinit var root: FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return FragmentNewsBinding.inflate(inflater, container, false).root
    }
}