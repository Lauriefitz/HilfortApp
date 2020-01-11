package com.example.archaeologyfieldwork.views.hillfortlist

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.RatingBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.archaeologyfieldwork.R
import com.example.archaeologyfieldwork.models.MainModel
import com.example.archaeologyfieldwork.views.BaseView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.info
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.widget.CompoundButton
import android.widget.ToggleButton




@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HillfortListView : BaseView(),
    hillfortListener {

    lateinit var presenter: HillfortListPresenter
    lateinit var hillfort: MainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        init(toolbar, false)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        presenter = initPresenter(HillfortListPresenter(this)) as HillfortListPresenter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadHillforts()

    }

    override fun showHillforts(hillforts: List<MainModel>) {
        recyclerView.adapter =
            hillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_top, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_fav -> presenter.doShowFavHillfort()
        }
        return super.onOptionsItemSelected(item)
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


        override fun onhillfortClick(hillfort: MainModel) {
        presenter.doEditHillfort(hillfort)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

}

