package com.narcyber.mvpbasics.presenter

import com.narcyber.mvpbasics.helper.ConstantHelper
import com.narcyber.mvpbasics.helper.DataSaveHelper
import com.narcyber.mvpbasics.model.User

class MainActivityPresenter(val view: MainActivityView, val dataSaveHelper: DataSaveHelper) :
    ParentPresenter() {

    fun findUserByEmailAndPassword(email: String, password: String) {
        userRepository.requestUserByEmailAndPassword(email, password)
    }

    fun rememberPasswordAndEmail(email: String, password: String) {
        dataSaveHelper.writeString(ConstantHelper.KEY_EMAIL, email)
        dataSaveHelper.writeString(ConstantHelper.KEY_PASSWORD, password)
    }

    fun sendPasswordAndEmailLastRegistered() {
        var email: String = dataSaveHelper.getString(ConstantHelper.KEY_EMAIL)
        var password: String = dataSaveHelper.getString(ConstantHelper.KEY_PASSWORD)
        if (!email.isEmpty() && !password.isEmpty()) {
            view.savedPasswordAndEmail(email, password)
        }
    }

    fun removeLocal() {
        dataSaveHelper.removeObject(ConstantHelper.LOCAL_PASSWORD)
        dataSaveHelper.removeObject(ConstantHelper.LOCAL_EMAIL)
    }

    fun clearData() {
        dataSaveHelper.clear()
    }

    override fun respondUserByEmailAndPassword(user: User?) {
        view.ifExistGetUsername(user)
    }

    interface MainActivityView {
        fun ifExistGetUsername(user: User?)

        fun savedPasswordAndEmail(email: String?, password: String?)
    }

}