package com.sgs.jewelsoft.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sgs.jewelsoft.MVVM.Report
import com.sgs.jewelsoft.databinding.ReceiptViewBinding
import com.sgs.jewelsoft.databinding.ReportViewBinding

class ReportAdapter(val context: Context) :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    var dashboardListener: ((locationModel: Report) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportAdapter.ReportViewHolder {
        return ReportViewHolder(
            ReportViewBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReportAdapter.ReportViewHolder, position: Int) {
        try {
            val reportModel = differ.currentList[position]
            holder.setView(reportModel)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ReportViewHolder(private var binding: ReportViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setView(view: Report) {
            binding.tvDate.text = view.date
            binding.tvName.text = view.lname
            binding.tvBalance.text = view.balance
            binding.tvTotal.text = view.total
        }
        init {

            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    try {
                        dashboardListener?.invoke(differ.currentList[position])

                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    }
                }
            }

        }
    }

    private val callback = object : DiffUtil.ItemCallback<Report>() {
        override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean {
            return oldItem.lname == newItem.lname
        }

        override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)


}