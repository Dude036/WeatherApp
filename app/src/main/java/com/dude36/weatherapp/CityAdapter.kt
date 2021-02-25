package com.dude36.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(internal var context: Context, internal var cityList: List<City>) : RecyclerView.Adapter<CityCard>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityCard {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return CityCard(layoutView)
    }

    override fun onBindViewHolder(holder: CityCard, position: Int) {
        holder.cityName.text = cityList[position].cityName
        holder.cityTempF.text = cityList[position].cityTempF.toString()
        holder.cityTempC.text = cityList[position].cityTempC.toString()
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

}