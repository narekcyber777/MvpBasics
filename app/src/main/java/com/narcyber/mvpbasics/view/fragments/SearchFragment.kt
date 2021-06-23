package com.narcyber.mvpbasics.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.narcyber.mvpbasics.R
import com.narcyber.mvpbasics.databinding.FragmentSearchContentBinding

class SearchFragment : Fragment() {
    lateinit var root: FragmentSearchContentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        root = FragmentSearchContentBinding.inflate(inflater, container, false)
        return root.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inIt()
    }

    private fun inIt() {
        root.v1.setOnClickListener {
            navigateTo(1)
        }
        root.v2.setOnClickListener {
            navigateTo(2)
        }
    }

    private fun navigateTo(i: Int) {
        when (i) {
            1 -> {
                findNavController().navigate(R.id.action_searchFragment_to_airlinesVOneFragment)
            }
            2 -> {
                findNavController().navigate(R.id.action_searchFragment_to_airlinesVTwoFragment)
            }
        }

    }
}