package com.anggaari.showcase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anggaari.showcase.databinding.EducationRowLayoutBinding
import com.anggaari.showcase.models.education.Education
import com.anggaari.showcase.models.education.EducationResult
import com.anggaari.showcase.utils.DataDiffUtil

class EducationAdapter : RecyclerView.Adapter<EducationAdapter.MyViewHolder>() {

    private var educations = emptyList<Education>()

    class MyViewHolder(private val binding: EducationRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(education: Education) {
            binding.titleTextView.text = education.institution
            binding.descriptionTextView.text = education.degree
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EducationRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentEducation = educations[position]
        holder.bind(currentEducation)
    }

    override fun getItemCount(): Int {
        return educations.size
    }

    fun setData(newData: EducationResult) {
        val dataDiffUtil = DataDiffUtil(educations, newData.data)
        val diffUtilResult = DiffUtil.calculateDiff(dataDiffUtil)
        educations = newData.data
        diffUtilResult.dispatchUpdatesTo(this)
    }
}