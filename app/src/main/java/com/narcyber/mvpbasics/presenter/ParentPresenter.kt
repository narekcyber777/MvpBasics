package com.narcyber.mvpbasics.presenter

import com.narcyber.mvpbasics.model.User
import com.narcyber.mvpbasics.repository.UserRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable


abstract class ParentPresenter : UserRepository.PresenterContract {
    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    val userRepository: UserRepository by lazy {
        UserRepository(this)
    }
    override val disposables: CompositeDisposable?
        get() = compositeDisposable

    override fun respondSuccessPushing() {
    }

    override fun respondPushingFailed(message: String?) {
    }

    override fun respondUserByEmail(user: User?) {
    }

    override fun respondUserByUserName(user: User?) {
    }

    override fun respondUserByEmailAndPassword(user: User?) {
    }

    override fun respondDeletedUserCount(count: Int) {
    }

    override fun respondFailedDeleteUser(message: String?) {
    }

    override fun respondForAllUsers(users: List<User?>?) {
    }

    open fun clearDisposables() {
        compositeDisposable.clear()
    }

}