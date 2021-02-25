package com.dude36.weatherapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityCard(itemView: View)  : RecyclerView.ViewHolder(itemView) {
    var layout: View = itemView.findViewById(R.id.cardLayout)
    var icon: ImageView = itemView.findViewById(R.id.iconView)
    var cityName : TextView = itemView.findViewById(R.id.cityName)
    var cityTempF : TextView = itemView.findViewById(R.id.cityTempF)
    var cityTempC : TextView = itemView.findViewById(R.id.cityTempC)
}
