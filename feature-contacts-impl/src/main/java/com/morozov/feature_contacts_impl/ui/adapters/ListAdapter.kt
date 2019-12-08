package com.morozov.feature_contacts_impl.ui.adapters

import androidx.recyclerview.widget.RecyclerView

abstract class ListAdapter<T, VH : RecyclerView.ViewHolder>: RecyclerView.Adapter<VH>() {

    private var data: List<T> = ArrayList()

    protected fun data(): List<T> = data

    fun setData(data: List<T>) {
        this.data = data
    }

    override fun getItemCount(): Int = data.size
}