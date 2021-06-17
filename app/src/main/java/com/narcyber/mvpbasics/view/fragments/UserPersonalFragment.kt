package com.narcyber.mvpbasics.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.narcyber.mvpbasics.databinding.FragmentUserPersonalBinding
import com.narcyber.mvpbasics.utils.moveToAndClear
import com.narcyber.mvpbasics.utils.removeLocal
import com.narcyber.mvpbasics.view.HomeActivity
import com.narcyber.mvpbasics.view.MainActivity

class UserPersonalFragment : Fragment() {
    lateinit var root: FragmentUserPersonalBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        root = FragmentUserPersonalBinding.inflate(inflater, container, false)
        return root.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inIt()
    }

    private fun inIt() {
        if (HomeActivity.user != null) {
            root.name.text = HomeActivity.user!!.fullName
            root.email.text = HomeActivity.user!!.email
            root.login.text = HomeActivity.user!!.userName
        }
        root.logOut.setOnClickListener {
            removeLocal(requireContext())
            moveToAndClear(requireActivity(), MainActivity::class.java)
        }
        root.allUsers.setOnClickListener {
            val navAction: UserPersonalFragmentDirections.
            ActionUserPersonalFragmentToAllUsersFragment =
                UserPersonalFragmentDirections.actionUserPersonalFragmentToAllUsersFragment(root.login.text.toString())
            findNavController().navigate(navAction)
        }

    }
}