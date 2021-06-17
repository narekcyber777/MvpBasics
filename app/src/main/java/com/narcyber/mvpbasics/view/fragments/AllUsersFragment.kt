package com.narcyber.mvpbasics.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.narcyber.mvpbasics.adapter.UserAdapter

import com.narcyber.mvpbasics.databinding.FragmentAllUsersBinding
import com.narcyber.mvpbasics.model.User
import com.narcyber.mvpbasics.presenter.AllUsersFragmentPresenter

class AllUsersFragment : Fragment(), AllUsersFragmentPresenter.AllUserView {
    lateinit var root: FragmentAllUsersBinding
    lateinit var presenter: AllUsersFragmentPresenter
    lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        root = FragmentAllUsersBinding.inflate(inflater, container, false)
        return root.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customizeWidgets()
        inIt()
    }

    private fun inIt() {
        presenter = AllUsersFragmentPresenter(this)
        presenter.getAllUsers()
        userAdapter = UserAdapter()
        root.recycler.adapter = userAdapter
        root.searchWidget.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun customizeWidgets() {
        root.recycler.addItemDecoration(
            DividerItemDecoration(
                root.root.context, DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun allUsers(userList: List<User?>?) {
        Log.d("NAR", userList.toString())
        userAdapter.submitList(userList)
        userAdapter.createItemBackUp()
    }

}