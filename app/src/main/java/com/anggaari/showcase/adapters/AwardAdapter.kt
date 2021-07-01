package com.anggaari.showcase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anggaari.showcase.databinding.AwardRowLayoutBinding
import com.anggaari.showcase.models.award.Data
import com.anggaari.showcase.models.award.Result
import com.anggaari.showcase.utils.DataDiffUtil

class AwardAdapter : RecyclerView.Adapter<AwardAdapter.MyViewHolder>() {

    private var awards = emptyList<Data>()

    class MyViewHolder(private val binding: AwardRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.titleTextView.text = data.title
            binding.descriptionTextView.text = data.description
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AwardRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentAward = awards[position]
        holder.bind(currentAward)
    }

    override fun getItemCount(): Int {
        return awards.size
    }

    fun setData(newData: Result) {
        val dataDiffUtil = DataDiffUtil(awards, newData.data)
        val diffUtilResult = DiffUtil.calculateDiff(dataDiffUtil)
        awards = newData.data
        diffUtilResult.dispatchUpdatesTo(this)
    }
}