package com.example.archaeologyfieldwork.views.favourites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.archaeologyfieldwork.R
import com.example.archaeologyfieldwork.models.MainModel
import kotlinx.android.synthetic.main.card_hillfort.view.*

interface hillfortListenerFav{
    fun onhillfortClick(hillfort: MainModel)
}

class hillfortAdapterFav constructor (
    private var hillforts: List<MainModel>,
    private val listener: hillfortListenerFav
):
    RecyclerView.Adapter<hillfortAdapterFav.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_hillfort,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hillfort = hillforts[holder.adapterPosition]
        holder.bind(hillfort, listener)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hillfort: MainModel, listener: hillfortListenerFav) {
            itemView.hillfortTitle.text = hillfort.title
            itemView.hillfortDescription.text = hillfort.description
            itemView.ratingBar.rating = hillfort.rating
            //itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image))
            Glide.with(itemView.context).load(hillfort.image).into(itemView.imageIcon)
            itemView.setOnClickListener{ listener.onhillfortClick(hillfort)}
        }
    }


}
