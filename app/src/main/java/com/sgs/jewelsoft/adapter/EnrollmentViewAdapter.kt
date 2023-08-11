package com.sgs.jewelsoft.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sgs.jewelsoft.MVVM.EnrollmentShow
import com.sgs.jewelsoft.databinding.EnrollmentDetailsViewBinding

class EnrollmentViewAdapter(val context: Context) :
    RecyclerView.Adapter<EnrollmentViewAdapter.EnrollmentShowViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EnrollmentViewAdapter.EnrollmentShowViewHolder {
        return EnrollmentShowViewHolder(
            EnrollmentDetailsViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: EnrollmentViewAdapter.EnrollmentShowViewHolder,
        position: Int
    ) {
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

    inner class EnrollmentShowViewHolder(private var binding: EnrollmentDetailsViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setView(view: EnrollmentShow) {
            binding.tvDue.text = view.due_no
            binding.tvDueDate.text = view.due_date
            binding.tvChitId.text = view.chit_id
            binding.tvAmount.text = view.amount
            binding.tvPaidAmt.text = view.paid_amount

        }

    }

    private val callback = object : DiffUtil.ItemCallback<EnrollmentShow>() {
        override fun areItemsTheSame(oldItem: EnrollmentShow, newItem: EnrollmentShow): Boolean {
            return oldItem.chit_id == newItem.chit_id
        }

        override fun areContentsTheSame(oldItem: EnrollmentShow, newItem: EnrollmentShow): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)

}