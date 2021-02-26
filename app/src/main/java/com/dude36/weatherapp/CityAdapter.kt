package com.dude36.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

/**Adapter for Recycler View. This will handle opening fragments, and connects the user to the data.
 *
 */
class CityAdapter(internal var context: Context, internal var cityList: List<City>) : RecyclerView.Adapter<CityCard>()  {
    /**Inflate Data into respective Cards.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityCard {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return CityCard(layoutView)
    }

    /**Fills in data after inflated
     * @param holder: CityCard          View to update info
     * @param position: Int             Place in the list of Cities
     */
    override fun onBindViewHolder(holder: CityCard, position: Int) {
        // Update Data
        holder.cityName.text = cityList[position].cityName
        holder.cityTempF.text = cityList[position].cityTempF.toString().format("%.2f") + " F°"
        holder.cityTempC.text = cityList[position].cityTempC.toString().format("%.2f") + " C°"

        // Set OnClickListener
        holder.layout.setOnClickListener {
            // TODO (Josh): Setup Fragment for Specific City
            Toast.makeText(context, holder.cityName.text as String? + " fragment here", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

}