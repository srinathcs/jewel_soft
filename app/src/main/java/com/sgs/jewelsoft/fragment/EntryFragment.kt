package com.sgs.jewelsoft.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sgs.jewelsoft.MVVM.JewelSoftRepository
import com.sgs.jewelsoft.MVVM.JewelSoftViewModel
import com.sgs.jewelsoft.MVVM.JewelViewModelFactory
import com.sgs.jewelsoft.MainPreference
import com.sgs.jewelsoft.Resources
import com.sgs.jewelsoft.databinding.FragmentEntryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EntryFragment : Fragment() {
    private lateinit var binding: FragmentEntryBinding
    private val jewelSoftVM: JewelSoftViewModel by lazy {
        val repos = JewelSoftRepository()
        val factory = JewelViewModelFactory(repos)
        ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]
    }
    private lateinit var mainPreference: MainPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mainPreference = MainPreference(requireContext())

        binding = FragmentEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // val repos = JewelSoftRepository()
        //val factory = JewelViewModelFactory(repos)
       // jewelSoftVM = ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]
        val currentDate = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate.time)
        binding.etAnniversary.setText(formattedDate)
        binding.etAnniversary.setOnClickListener {
            showAnniversaryPickerDialog()
        }
        binding.etDob.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.success(
                "2",
                mainPreference.getCid().first(),
                "0",
                mainPreference.getUserId().first(),
                binding.etName.text.toString(),
                binding.etDob.text.toString(),
                binding.etMobile.text.toString(),
                binding.etAnniversary.text.toString(),
                binding.etPresentAddress.text.toString(),
                binding.etPrementAddress.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPanNum.text.toString(),
                binding.etAadharNum.text.toString()
            )
            validationSuccess()
        }
    }

    private fun validationSuccess() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.testSuccessFlow.collect {
                when (it) {
                    is Resources.Error -> {
                        Log.i("TAG", "validate_for_error: ${it.message.toString()}")

                    }

                    is Resources.Loading -> {

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "validationSuccess: ${it.data}")
                        if (it.data!!.error == false) {
                            if (it.data!!.error_msg == "Customer Save successfully") {
                                binding.etAadharNum.setText("")
                                binding.etPanNum.setText("")
                                binding.etEmail.setText("")
                                binding.etPrementAddress.setText("")
                                binding.etPresentAddress.setText("")
                                binding.etAnniversary.setText("")
                                binding.etMobile.setText("")
                                binding.etDob.setText("")
                                binding.etName.setText("")
                                Toast.makeText(
                                    requireContext(),
                                    "Data Added Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                requireContext(), "Data is not added", Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = "$day/${month + 1}/$year"
                binding.etDob.setText(selectedDate)
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

    private fun showAnniversaryPickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate =
                    "$dayOfMonth/${month + 1}/$year"
                binding.etAnniversary.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.calendarViewShown = false
        datePickerDialog.datePicker.spinnersShown = true
        datePickerDialog.show()
    }
}
