package com.narcyber.mvpbasics.presenter

import com.narcyber.mvpbasics.model.User

class RegisterActivityPresenter(val view: RegisterView) :
    ParentPresenter() {
    fun isUserNameTaken(userName: String = ""): Boolean {
        userRepository.requestUserByUsername(userName)
        return true
    }

    fun isEmailTaken(email: String = ""): Boolean {
        userRepository.requestUserByEmail(email)
        return true
    }

    fun pushUserIntoDB(
        email: String,
        userName: String,
        name: String,
        password: String,
    ) {
        val user = User(email = email, fullName = name, userName = userName, password = password)
        userRepository.requestUserPush(user)
    }

    override fun respondSuccessPushing() {
        view.notifyUserSuccessRegistered()
    }

    override fun respondPushingFailed(message: String?) {
        view.notifyUserRegFailed()
    }

    override fun respondUserByEmail(user: User?) {
        if (user == null) {
            view.isEmailUsed(false)
            return
        }
        view.isEmailUsed(true)
    }

    override fun respondUserByUserName(user: User?) {
        if (user == null) {
            view.isUsernameUsed(false)
            return
        }
        view.isUsernameUsed(true)
    }

    interface RegisterView {
        fun isEmailUsed(isUsed: Boolean): Boolean
        fun isUsernameUsed(isUsed: Boolean): Boolean
        fun notifyUserSuccessRegistered(): Boolean
        fun notifyUserRegFailed(): Boolean
    }

}