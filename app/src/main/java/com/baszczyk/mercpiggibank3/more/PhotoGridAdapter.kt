package com.baszczyk.mercpiggibank3.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baszczyk.mercpiggibank3.databinding.GridViewItemBinding
import com.baszczyk.mercpiggibank3.network.MercedesPhoto

class PhotoGridAdapter : ListAdapter<MercedesPhoto, PhotoGridAdapter.MercedesPhotoViewHolder>(DiffCallback) {


    class MercedesPhotoViewHolder(private var binding: GridViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mercedesPhoto: MercedesPhoto) {
            binding.photo = mercedesPhoto

            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MercedesPhoto>() {
        override fun areItemsTheSame(oldItem: MercedesPhoto, newItem: MercedesPhoto): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MercedesPhoto, newItem: MercedesPhoto): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MercedesPhotoViewHolder {
        return MercedesPhotoViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MercedesPhotoViewHolder, position: Int) {
        val mercedesPhoto = getItem(position)
        holder.bind(mercedesPhoto)
    }
}