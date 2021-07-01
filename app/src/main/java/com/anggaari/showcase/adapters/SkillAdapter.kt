package com.anggaari.showcase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anggaari.showcase.databinding.SkillRowLayoutBinding
import com.anggaari.showcase.models.skill.Skill
import com.anggaari.showcase.models.skill.SkillResult
import com.anggaari.showcase.utils.DataDiffUtil

class SkillAdapter : RecyclerView.Adapter<SkillAdapter.MyViewHolder>() {

    private var skills = emptyList<Skill>()

    class MyViewHolder(private val binding: SkillRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(skill: Skill) {
            binding.titleTextView.text = skill.expertise
            binding.descriptionTextView.text = skill.description
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SkillRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentSkill = skills[position]
        holder.bind(currentSkill)
    }

    override fun getItemCount(): Int {
        return skills.size
    }

    fun setData(newData: SkillResult) {
        val dataDiffUtil = DataDiffUtil(skills, newData.data)
        val diffUtilResult = DiffUtil.calculateDiff(dataDiffUtil)
        skills = newData.data
        diffUtilResult.dispatchUpdatesTo(this)
    }
}