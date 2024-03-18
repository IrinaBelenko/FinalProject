package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewAdapter (private val items: List<Results>): RecyclerView.Adapter<RecycleViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val listItemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return RecycleViewHolder(listItemView)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.lat.text = "lat: ${String.format("%.6f",items[position].geometry.location.lat)}"
        holder.lng.text = "lng: ${String.format("%.6f",items[position].geometry.location.lng)}"
        val reference = items[position].photos[0].photoReference
        val request =   "https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photo_reference=$reference&key=AIzaSyDJber0UpLCHpq3F7zYxs5Xww1IEdgbo78"
        Picasso.get().load(request).into(holder.image)
    }
}

class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView = itemView.findViewById(R.id.listName)
    val lat: TextView = itemView.findViewById(R.id.listLat)
    val lng: TextView = itemView.findViewById(R.id.listLng)
    val image: ImageView = itemView.findViewById(R.id.listImage)
}