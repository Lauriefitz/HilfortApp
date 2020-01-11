package com.example.archaeologyfieldwork.views.account

import com.example.archaeologyfieldwork.main.MainApp
import com.example.archaeologyfieldwork.views.BasePresenter
import com.example.archaeologyfieldwork.views.BaseView
import com.example.archaeologyfieldwork.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast


class AccountPresenter(view: BaseView) : BasePresenter(view){

    init {
        app = view.application as MainApp
    }

    fun doUpdatePassword(oldPass: String, newPass: String, confPass: String){
        val user = FirebaseAuth.getInstance().currentUser
        val oldPassword = oldPass
        val newPassword = newPass
        val confirmPassword = confPass

        if (oldPassword == newPassword){
            view?.toast("You can not use your previous password.")
            view?.navigateTo(VIEW.ACCOUNT)
        }else{
            if (newPassword == confirmPassword){
                user!!.updatePassword(newPassword).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        view?.toast("User password updated.")
                    } else{
                        view?.toast("Password Update Failed: ${task.exception?.message}")
                    }
                }
                FirebaseAuth.getInstance().signOut()
                view?.navigateTo(VIEW.LOGIN)
            } else {
                view?.toast("New Password and Confirmation do not Match!")
                view?.navigateTo(VIEW.ACCOUNT)
            }

        }
    }

    fun doGoList() {
        view?.navigateTo(VIEW.LIST)
    }

}
