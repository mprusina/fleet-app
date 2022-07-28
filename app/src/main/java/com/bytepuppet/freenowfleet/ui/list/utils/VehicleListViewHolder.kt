package com.bytepuppet.freenowfleet.ui.list.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bytepuppet.freenowfleet.data.entities.Poi
import com.bytepuppet.freenowfleet.databinding.FragmentVehicleListRowBinding

class VehicleListViewHolder(
    private val binding: FragmentVehicleListRowBinding,
    private val onMyClickListener: VehicleListAdapter.OnMyClickListener
) : RecyclerView.ViewHolder(binding.root),
    View.OnClickListener {

    private lateinit var item: Poi

    fun bind(item: Poi) {
        this.item = item

        binding.vehicleId.text = item.id.toString()
        binding.vehicleType.text = item.fleetType
        binding.vehicleLocation.text = item.street

        binding.root.setOnClickListener(this)
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onMyClickListener: VehicleListAdapter.OnMyClickListener
        ): VehicleListViewHolder {
            val vehicleListItemBinding =
                FragmentVehicleListRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            return VehicleListViewHolder(vehicleListItemBinding, onMyClickListener)
        }
    }

    override fun onClick(v: View?) {
        this.onMyClickListener.onItemClick(bindingAdapterPosition, item)
    }
}