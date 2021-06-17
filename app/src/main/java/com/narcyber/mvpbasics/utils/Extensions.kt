package com.narcyber.mvpbasics.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.narcyber.mvpbasics.helper.ConstantHelper
import com.narcyber.mvpbasics.helper.DataSaveHelper

fun showInToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

}

fun moveTo(from: FragmentActivity, cls: Class<*>?) {
    val intent = Intent(from, cls)
    from.startActivity(intent)
}

fun removeLocal(context: Context) {
    val db = DataSaveHelper(context)
    db.removeObject(ConstantHelper.KEY_EMAIL)
    db.removeObject(ConstantHelper.KEY_PASSWORD)
}

fun moveToAndClear(from: FragmentActivity, cls: Class<*>?) {
    val intent = Intent(from, cls)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    from.startActivity(intent)
}

fun withArgumentsMoveToAndClear(bundle: Bundle?, from: FragmentActivity, cls: Class<*>?) {
    val intent = Intent(from, cls)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    intent.putExtras(bundle!!)
    from.startActivity(intent)
}