package com.dude36.weatherapp

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(internal var context: Context, internal var cityList: List<City>) : RecyclerView.Adapter<CityCard>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityCard {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CityCard, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

}