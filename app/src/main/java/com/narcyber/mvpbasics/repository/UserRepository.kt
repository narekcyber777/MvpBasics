package com.narcyber.mvpbasics.repository

import android.util.Log
import com.narcyber.mvpbasics.db.RoomDb
import com.narcyber.mvpbasics.model.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class UserRepository(val presenter: PresenterContract) {
    fun requestForAllUsers() {
        RoomDb.instance.userDao().getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<User>> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: List<User>?) {
                    presenter.respondForAllUsers(t)

                }

                override fun onError(e: Throwable?) {

                }

                override fun onComplete() {

                }

            })

    }

    fun requestDeleteUser(user: User) {
        RoomDb.instance.userDao().deleteUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Int?> {
                override fun onSubscribe(d: @NonNull Disposable?) {
                    presenter.disposables?.add(d)
                }

                override fun onSuccess(integer: @NonNull Int?) {
                    presenter.respondDeletedUserCount(integer!!)
                }

                override fun onError(e: @NonNull Throwable?) {
                    presenter.respondFailedDeleteUser(e.toString())
                }
            })
    }


    fun requestUserByEmailAndPassword(email: String, password: String) {
        RoomDb.instance.userDao().getUserByEmailAndPassword(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<User?> {
                override fun onSubscribe(d: @NonNull Disposable?) {
                    presenter.disposables?.add(d)
                }

                override fun onSuccess(user: @NonNull User?) {
                    presenter.respondUserByEmailAndPassword(user)
                }

                override fun onError(e: @NonNull Throwable?) {
                    presenter.respondUserByEmailAndPassword(null)
                }
            })
    }

    fun requestUserByUsername(userName: String) {
        RoomDb.instance.userDao().getUserByUsername(userName)
            .subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<User?> {
                override fun onSubscribe(d: @NonNull Disposable?) {
                    presenter.disposables?.add(d)
                }

                override fun onSuccess(user: @NonNull User?) {
                    presenter.respondUserByUserName(user)
                }

                override fun onError(e: @NonNull Throwable?) {
                    presenter.respondUserByUserName(null)
                }
            })
    }

    fun requestUserByEmail(email: String) {
        RoomDb.instance.userDao().getUserByEmail(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<User?> {
                override fun onSubscribe(d: @NonNull Disposable?) {
                    presenter.disposables?.add(d)
                }

                override fun onSuccess(user: @NonNull User?) {
                    presenter.respondUserByEmail(user)
                }

                override fun onError(e: @NonNull Throwable?) {
                    presenter.respondUserByEmail(null)
                }
            })
    }

    fun requestUserPush(user: User) {
        RoomDb.instance.userDao().insertOrUpdate(user).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: @NonNull Disposable?) {
                    presenter.disposables?.add(d)
                }

                override fun onComplete() {
                    presenter.respondSuccessPushing()
                }

                override fun onError(e: @NonNull Throwable?) {
                    presenter.respondPushingFailed(
                        e!!.message
                    )
                    Log.d("NAR", e.message.toString())
                }
            })
    }


    interface PresenterContract {
        val disposables: CompositeDisposable?
        fun respondSuccessPushing()
        fun respondPushingFailed(message: String?)
        fun respondUserByEmail(user: User?)
        fun respondUserByUserName(user: User?)
        fun respondUserByEmailAndPassword(user: User?)
        fun respondDeletedUserCount(count: Int)
        fun respondFailedDeleteUser(message: String?)
        fun respondForAllUsers(users: List<User?>?)
    }
}