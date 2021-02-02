package com.manikandan.pexels.util

import androidx.recyclerview.widget.DiffUtil
import com.manikandan.pexels.modal.Photo

class PexelDiffUtil(private val oldList:List<Photo>, private val newList: List<Photo>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}