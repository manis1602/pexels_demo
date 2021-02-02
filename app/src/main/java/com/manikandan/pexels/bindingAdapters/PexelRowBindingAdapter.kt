package com.manikandan.pexels.bindingAdapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

class PexelRowBindingAdapter {
    companion object{
        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, url: String){
            imageView.load(url){
                crossfade(600)
            }
        }

        @BindingAdapter("setImageHeight")
        @JvmStatic
        fun setImageHeight(imageView: ImageView, height: String){

        }
    }
}