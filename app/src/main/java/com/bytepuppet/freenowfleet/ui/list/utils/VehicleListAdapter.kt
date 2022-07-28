package com.bytepuppet.freenowfleet.ui.list.utils

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bytepuppet.freenowfleet.data.entities.Poi

class VehicleListAdapter(private val onMyClickListener: OnMyClickListener) :
    ListAdapter<Poi, VehicleListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListViewHolder {
        return VehicleListViewHolder.create(parent, this.onMyClickListener)
    }

    override fun onBindViewHolder(holder: VehicleListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Poi>() {
            override fun areItemsTheSame(oldItem: Poi, newItem: Poi): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Poi, newItem: Poi): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnMyClickListener {
        fun onItemClick(position: Int, item: Poi)
    }
}