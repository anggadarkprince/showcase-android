package com.anggaari.showcase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.anggaari.showcase.databinding.PortfolioRowLayoutBinding
import com.anggaari.showcase.models.portfolio.Portfolio
import com.anggaari.showcase.models.portfolio.PortfolioResult
import com.anggaari.showcase.utils.DataDiffUtil

class PortfolioAdapter : RecyclerView.Adapter<PortfolioAdapter.MyViewHolder>() {

    private var portfolios = emptyList<Portfolio>()

    class MyViewHolder(private val binding: PortfolioRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(portfolio: Portfolio) {
            binding.titleTextView.text = portfolio.title
            binding.descriptionTextView.text = portfolio.tagline
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PortfolioRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPortfolio = portfolios[position]
        holder.bind(currentPortfolio)
    }

    override fun getItemCount(): Int {
        return portfolios.size
    }

    fun setData(newData: PortfolioResult) {
        val dataDiffUtil = DataDiffUtil(portfolios, newData.data)
        val diffUtilResult = DiffUtil.calculateDiff(dataDiffUtil)
        portfolios = newData.data
        diffUtilResult.dispatchUpdatesTo(this)
    }
}