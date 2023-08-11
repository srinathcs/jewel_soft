package com.sgs.jewelsoft.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sgs.jewelsoft.MVVM.Enrollment
import com.sgs.jewelsoft.MVVM.EnrollmentTwo
import com.sgs.jewelsoft.MVVM.Report
import com.sgs.jewelsoft.MVVM.ViewReceipt
import com.sgs.jewelsoft.databinding.EnrollmentViewBinding
import com.sgs.jewelsoft.databinding.ReportViewBinding

class EnrollmentAdapter(val context: Context) :
    RecyclerView.Adapter<EnrollmentAdapter.EnrollmentViewHolder>() {
    var dashboardListener: ((locationModel: EnrollmentTwo) -> Unit)? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EnrollmentAdapter.EnrollmentViewHolder {
        return EnrollmentViewHolder(
            EnrollmentViewBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: EnrollmentAdapter.EnrollmentViewHolder, position: Int) {
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

    inner class EnrollmentViewHolder(private var binding: EnrollmentViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setView(view: EnrollmentTwo) {
            binding.tvDate.text = view.date
            binding.tvmDate.text = view.mdate
            binding.tvNameD.text = view.lname
            binding.tvChitIdD.text = view.chit_id
            binding.tvSchemeD.text = view.sch_name
        }

        init {

            binding.tvSeeMore.setOnClickListener {
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

    private val callback = object : DiffUtil.ItemCallback<EnrollmentTwo>() {
        override fun areItemsTheSame(oldItem: EnrollmentTwo, newItem: EnrollmentTwo): Boolean {
            return oldItem.lname == newItem.lname
        }

        override fun areContentsTheSame(oldItem: EnrollmentTwo, newItem: EnrollmentTwo): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, callback)

}
