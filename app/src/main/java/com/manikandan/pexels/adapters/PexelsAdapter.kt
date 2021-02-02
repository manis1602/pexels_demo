package com.manikandan.pexels.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.manikandan.pexels.databinding.PexelRowLayoutBinding
import com.manikandan.pexels.modal.PexelImage
import com.manikandan.pexels.modal.Photo
import com.manikandan.pexels.util.PexelDiffUtil

class PexelsAdapter: RecyclerView.Adapter<PexelsAdapter.MyViewHolder>() {
    private var photos = emptyList<Photo>()

    class MyViewHolder(private val binding: PexelRowLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo){
            binding.photo = photo
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PexelRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPhoto = photos[position]
        holder.bind(currentPhoto)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun setNewData(pexelImage: PexelImage){
        val pexelDiffUtil = PexelDiffUtil(photos, pexelImage.photos)
        val diffUtilResult = DiffUtil.calculateDiff(pexelDiffUtil)
        photos = pexelImage.photos
        diffUtilResult.dispatchUpdatesTo(this)
    }
}