package com.geekbrains.nasaexplorer.ui.asteroids_neows

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.nasaexplorer.R
import com.google.android.material.textview.MaterialTextView
import okhttp3.internal.format

class AsteroidsNeoWSFragmentAdapter :
    RecyclerView.Adapter<AsteroidsNeoWSFragmentAdapter.AsteroidsViewHolder>() {
    private var asteroidsDataset: AsteroidsNeoWSFragmentDataset = AsteroidsNeoWSFragmentDataset()

    fun setAsteroid(data: AsteroidsNeoWSFragmentDataset) {
        asteroidsDataset = data
        notifyDataSetChanged()
    }

    inner class AsteroidsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(asteroidDataset: AsteroidDataset) {
            with(itemView) {

                findViewById<MaterialTextView>(R.id.fragment_asteroidsneows_recycler_item_id)
                    .text = format(
                    resources.getString(R.string.fragment_asteroidsneows_recycler_item_id),
                    asteroidDataset.id
                )

                findViewById<MaterialTextView>(R.id.fragment_asteroidsneows_recycler_item_name)
                    .text = format(
                    resources.getString(R.string.fragment_asteroidsneows_recycler_item_name),
                    asteroidDataset.name
                )

                findViewById<MaterialTextView>(R.id.fragment_asteroidsneows_recycler_item_magnitude)
                    .text = format(
                    resources.getString(R.string.fragment_asteroidsneows_recycler_item_magnitude),
                    asteroidDataset.absoluteMagnitudeH
                )

                findViewById<MaterialTextView>(
                    R.id.fragment_asteroidsneows_recycler_item_diameter_min
                )
                    .text = format(
                    resources.getString(
                        R.string.fragment_asteroidsneows_recycler_item_diameter_min
                    ),
                    asteroidDataset.estimatedDiameterMin
                )

                findViewById<MaterialTextView>(
                    R.id.fragment_asteroidsneows_recycler_item_diameter_max
                ).text = format(
                    resources.getString(
                        R.string.fragment_asteroidsneows_recycler_item_diameter_max
                    ),
                    asteroidDataset.estimatedDiameterMax
                )

                findViewById<MaterialTextView>(
                    R.id.fragment_asteroidsneows_recycler_item_hazardous
                ).text = format(
                    resources.getString(R.string.fragment_asteroidsneows_recycler_item_hazardous),
                    asteroidDataset.hazardous
                )

                findViewById<MaterialTextView>(
                    R.id.fragment_asteroidsneows_recycler_item_relative_velocity
                ).text = format(
                    resources.getString(
                        R.string.fragment_asteroidsneows_recycler_item_relative_velocity
                    ),
                    asteroidDataset.relativeVelocity
                )

                findViewById<MaterialTextView>(
                    R.id.fragment_asteroidsneows_recycler_item_approach_date
                ).text = format(
                    resources.getString(
                        R.string.fragment_asteroidsneows_recycler_item_approach_date
                    ),
                    asteroidDataset.closeApproachDate
                )

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AsteroidsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_asteroidsneows_recycler_item,
                parent, false
            ) as View
        )

    override fun onBindViewHolder(holder: AsteroidsViewHolder, position: Int) {
        asteroidsDataset.asteroids.let { list ->
            holder.bind(list[position])
        }
    }

    override fun getItemCount() = asteroidsDataset.asteroids.size
}
