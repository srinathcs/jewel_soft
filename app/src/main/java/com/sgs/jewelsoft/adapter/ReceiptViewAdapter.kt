package com.sgs.jewelsoft.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sgs.jewelsoft.MVVM.ViewReceipt
import com.sgs.jewelsoft.databinding.ReceiptViewBinding

class ReceiptViewAdapter(val context: Context) :
    RecyclerView.Adapter<ReceiptViewAdapter.ReceiptViewHolder>() {

    var dashboardListener: ((locationModel: ViewReceipt) -> Unit)? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReceiptViewAdapter.ReceiptViewHolder {
        return ReceiptViewHolder(
            ReceiptViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReceiptViewAdapter.ReceiptViewHolder, position: Int) {
        try {
            val statusModel = differ.currentList[position]
            holder.setView(statusModel)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ReceiptViewHolder(private var binding: ReceiptViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setView(view: ViewReceipt) {
            binding.tvDate.text = view.date
            binding.tvAccount.text = view.account
            binding.tvBalance.text = view.balance
            binding.tvName.text = view.lname
            binding.tvTotal.text = view.total
            binding.tvPType.text = view.ptype
            binding.tvRemark.text = view.remark
            binding.tvChitId.text = view.chit_id
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

    private val callback = object : DiffUtil.ItemCallback<ViewReceipt>() {
        override fun areItemsTheSame(oldItem: ViewReceipt, newItem: ViewReceipt): Boolean {
            return oldItem.lname == newItem.lname
        }

        override fun areContentsTheSame(oldItem: ViewReceipt, newItem: ViewReceipt): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)

}