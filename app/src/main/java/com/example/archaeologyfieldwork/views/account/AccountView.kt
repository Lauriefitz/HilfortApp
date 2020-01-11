package com.example.archaeologyfieldwork.views.account

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.example.archaeologyfieldwork.R
import com.example.archaeologyfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_account.*
import org.jetbrains.anko.info

class AccountView: BaseView() {

    lateinit var presenter: AccountPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        setSupportActionBar(toolbar)

        super.init(toolbar, true)
        presenter = initPresenter(AccountPresenter(this)) as AccountPresenter

        button_savePass.setOnClickListener{ presenter.doUpdatePassword(editOldPass.text.toString(), editNewPass.text.toString(), editConfPass.text.toString())}
        button_cancelPass.setOnClickListener { presenter.doGoList() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }
}