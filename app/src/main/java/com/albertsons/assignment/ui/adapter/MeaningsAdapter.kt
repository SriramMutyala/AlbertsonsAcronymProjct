package com.albertsons.assignment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.albertsons.assignment.data.model.LFS
import com.albertsons.assignment.databinding.ListItemMeaningBinding

class MeaningsAdapter(private val meaningsList: List<LFS>) : RecyclerView.Adapter<MeaningsAdapter.MeaningsItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningsAdapter.MeaningsItemViewHolder {
        return MeaningsItemViewHolder(ListItemMeaningBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: MeaningsItemViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return meaningsList.size
    }

    inner class MeaningsItemViewHolder constructor(private val binding: ListItemMeaningBinding) :
        RecyclerView.ViewHolder
            (binding.root) {
        fun onBind(position: Int) {
            binding.textViewGenres.text = meaningsList[position].fullForm
        }
    }
}