package com.sgs.jewelsoft.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sgs.jewelsoft.MVVM.JewelSoftRepository
import com.sgs.jewelsoft.MVVM.JewelSoftViewModel
import com.sgs.jewelsoft.MVVM.JewelViewModelFactory
import com.sgs.jewelsoft.MainPreference
import com.sgs.jewelsoft.Resources
import com.sgs.jewelsoft.adapter.ReportAdapter
import com.sgs.jewelsoft.databinding.FragmentReportBinding
import kotlinx.coroutines.flow.first
import java.util.Calendar

class ReportFragment : Fragment() {
    private lateinit var binding: FragmentReportBinding
    private lateinit var jewelSoftVM: JewelSoftViewModel
    private lateinit var mainPreference: MainPreference
    private lateinit var myadapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)
        mainPreference = MainPreference(requireContext())

        val repos = JewelSoftRepository()
        val factory = JewelViewModelFactory(repos)
        jewelSoftVM = ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]
        binding.btnShow.setOnClickListener {
            viewReport()
        }
        binding.etFromDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.etToDate.setOnClickListener {
            showToDatePickerDialog()
        }
        return binding.root
    }

    private fun viewReport() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.viewReport(
                "7",
                binding.etToDate.text.toString(),
                binding.etFromDate.text.toString(),
                mainPreference.getCid().first()
            )
        }
        stateViewReport()
    }

    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = "$day-${month + 1}-$year"
                binding.etFromDate.setText(selectedDate)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.calendarViewShown = false
        datePickerDialog.setTitle(null)
        datePickerDialog.datePicker.maxDate = currentDate.timeInMillis
        datePickerDialog.show()
    }

    private fun showToDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = "$day-${month + 1}-$year"
                binding.etToDate.setText(selectedDate)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.calendarViewShown = false
        datePickerDialog.setTitle(null)
        datePickerDialog.datePicker.maxDate = currentDate.timeInMillis
        datePickerDialog.show()
    }

    private fun stateViewReport() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.viewReportFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "stateViewReceipt:${it.data} ")

                        if (it.data.isNullOrEmpty()){
                            binding.horizontal1.visibility = View.GONE
                        }else{
                            binding.horizontal1.visibility = View.VISIBLE
                            myadapter = ReportAdapter(requireContext())
                            binding.recycle.adapter = myadapter
                            binding.recycle.layoutManager = LinearLayoutManager(requireContext())
                            myadapter.differ.submitList(it.data)
                        }
                    }
                }
            }
        }
    }
}