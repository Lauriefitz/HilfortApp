package com.example.archaeologyfieldwork.views.favourites

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.archaeologyfieldwork.R
import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.views.BaseView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.info

class HillfortListFavouritesView  : BaseView(),
    hillfortListenerFav {

    lateinit var presenter: HillfortListFavouritesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        init(toolbar, true)

        presenter = initPresenter(HillfortListFavouritesPresenter(this)) as HillfortListFavouritesPresenter

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.doShowFavHillfort()

    }

    override fun showHillforts(hillforts: List<MainModel>) {
        //hillforts = hillforts.filter { hillfort.favourite }
        recyclerView.adapter =
            hillfortAdapterFav(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }


    override fun onhillfortClick(hillfort: MainModel) {
        presenter.doEditHillfort(hillfort)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.item_add -> presenter.doAddHillfort()
            R.id.item_map -> presenter.doShowHillfortsMap()
            R.id.item_logout -> presenter.doLogout()
            R.id.item_refresh -> {

            }
            R.id.item_settings -> {
                presenter.doAccountView()
                info("Settings Pressed")
            }
        }
        false
    }
}
