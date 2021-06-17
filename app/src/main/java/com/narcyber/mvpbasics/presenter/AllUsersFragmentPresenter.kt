package com.narcyber.mvpbasics.presenter

import com.narcyber.mvpbasics.model.User

class AllUsersFragmentPresenter(val view: AllUserView) : ParentPresenter() {

    fun getAllUsers() {
        userRepository.requestForAllUsers()
    }

    override fun respondForAllUsers(users: List<User?>?) {
        view.allUsers(users)
    }

    interface AllUserView {
        fun allUsers(userList: List<User?>?)
    }
}