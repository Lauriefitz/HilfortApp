package com.example.archaeologyfieldwork.views.login

import android.util.Log
import com.example.archaeologyfieldwork.models.firebase.HillfortFireStore
import com.example.archaeologyfieldwork.views.BasePresenter
import com.example.archaeologyfieldwork.views.BaseView
import com.example.archaeologyfieldwork.views.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.toast
import com.google.android.gms.tasks.Task
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener



class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: HillfortFireStore? = null

    init {
        if(app.hillforts is HillfortFireStore){
            fireStore = app.hillforts as HillfortFireStore
        }
    }

    fun doLogin(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchHillforts {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }
            }
            else {
                view?.hideProgress()
                view?.toast("Log In Failed: ${task.exception?.message}")
            }
        }
    }

    fun doSignUp(email: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                view?.hideProgress()
                view?.navigateTo(VIEW.LIST)
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun doResetPassword(email: String){
        // If the email is empty, ask to put email in the box
        if (email == "") {
            view?.toast("Please enter email above!")
        }
        else{
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    view?.toast("Email sent.")
                }
            }
        }
    }

//    fun doLogout() {
//        FirebaseAuth.getInstance().signOut()
//        app.hillforts.clear()
//        view?.navigateTo(VIEW.LOGIN)
//    }
}
