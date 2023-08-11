package com.sgs.jewelsoft.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sgs.jewelsoft.MVVM.JewelSoftRepository
import com.sgs.jewelsoft.MVVM.JewelSoftViewModel
import com.sgs.jewelsoft.MVVM.JewelViewModelFactory
import com.sgs.jewelsoft.MainPreference
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.Resources
import com.sgs.jewelsoft.activity.DashBoardActivity
import com.sgs.jewelsoft.databinding.FragmentCustomerReportBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CustomerReportFragment : Fragment() {
    private lateinit var binding: FragmentCustomerReportBinding
    private lateinit var etAmount: EditText
    private lateinit var etTotalAmount: EditText
    private lateinit var jewelSoftVM: JewelSoftViewModel
    private lateinit var mainPreference: MainPreference
    private var custId = ""
    private var formattedDate = ""
    private var customerName: MutableList<String> = mutableListOf()
    private var customerId: MutableList<String> = mutableListOf()
    private var customerLabel: MutableList<String> = mutableListOf()
    private var customerMobile: MutableList<String> = mutableListOf()
    private var salesName: MutableList<String> = mutableListOf()
    private var salesId: MutableList<String> = mutableListOf()
    private var nameList: MutableList<String> = mutableListOf()
    private var idList: MutableList<String> = mutableListOf()
    private var scheme = ""
    private var sales = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainPreference = MainPreference(requireContext())
        val repos = JewelSoftRepository()
        val factory = JewelViewModelFactory(repos)
        jewelSoftVM = ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]


        binding = FragmentCustomerReportBinding.inflate(inflater, container, false)

        val currentDate = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        formattedDate = dateFormat.format(currentDate.time)
        binding.atvSDate.setText(formattedDate)

        binding.btnSave.setOnClickListener {
            saveEnrollment()
        }

        binding.atvSDate.setOnClickListener {
            showAnniversaryPickerDialog()
        }
        etAmount = binding.atvAmount
        cusName()
        sales()
        etTotalAmount = binding.atvTotalAmount

        etAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                calculateTotalAmount()
            }
        })

        binding.btnDocument.setOnClickListener {
            findNavController().navigate(R.id.documentUploadFragment)
        }
        return binding.root
    }

    private fun saveEnrollment() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.saveEnrollment(
                mainPreference.getCid().first(),
                mainPreference.getUserId().first(),
                binding.atvName.text.toString(),
                custId,
                scheme,
                formattedDate,
                binding.atvEMI.text.toString(),
                binding.atvFreeEMI.text.toString(),
                binding.atvPurity.text.toString(),
                binding.atvAmount.text.toString(),
                binding.atvEDate.text.toString(),
                binding.atvTotalAmount.text.toString(),
                binding.atvRemark.text.toString(),
                sales,
                "10"
            )
        }
        saveData()
    }

    private fun saveData() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.saveEnrollmentFlow.collect {
                when (it) {
                    is Resources.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                        binding.btnSave.visibility = View.GONE
                    }

                    is Resources.Error -> {
                        Log.i("TAG", "validate_for_error2: ${it.message.toString()}")
                        Toast.makeText(requireContext(), "Check Internet", Toast.LENGTH_SHORT).show()
                        binding.progress.visibility = View.VISIBLE
                        binding.btnSave.visibility = View.GONE
                        delay(1000)
                        binding.progress.visibility = View.GONE
                        binding.btnSave.visibility = View.VISIBLE
                    }

                    is Resources.Success -> {

                        binding.progress.visibility = View.GONE
                        binding.btnSave.visibility = View.VISIBLE

                        Log.i("TAG", "validationSuccess: ${it.data}")

                        val i = it.data!!
                        if (!i.error) {
                            binding.atvName.setText("")
                            binding.atvSchmeType.setText("")
                            binding.atvEDate.setText("")
                            binding.atvEMI.setText("")
                            binding.atvFreeEMI.setText("")
                            binding.atvPurity.setText("")
                            binding.atvAmount.setText("")
                            binding.atvTotalAmount.setText("")
                            binding.atvRemark.setText("")
                            binding.atvSales.setText("")

                            Toast.makeText(requireContext(),"Save Successfully",Toast.LENGTH_SHORT).show()

                            val int = Intent(requireActivity(),DashBoardActivity::class.java)
                            startActivity(int)
                            requireActivity().finish()

                        }
                    }
                }
            }
        }
    }

    private fun sales() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.sales(
                "11",
                mainPreference.getCid().first(),
                "3"
            )
        }
        salesMan()
    }

    private fun salesMan() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.salesFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "salesMan:${it.message} ")
                    }

                    is Resources.Success -> {
                        Log.i("TAG", "salesMan:${it.data} ")
                        try {


                            salesName.clear()
                            salesId.clear()
                            for (i in it.data!!) {
                                salesName.add(i.name)
                                salesId.add(i.id)
                            }
                            val arrayAdapter = ArrayAdapter(
                                requireContext(),
                                R.layout.complete_text_view, salesName
                            )
                            binding.atvSales.setAdapter(arrayAdapter)
                            binding.atvSales.threshold = 1
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                }
            }
        }

        binding.atvSales.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            for (i in 0 until salesName.size) {
                try {
                    if (binding.atvSales.text.toString() == salesName[i]) {
                        sales = salesId[i]

                        Log.i("TAG", "forsalessss:$id")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }

        }
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
                binding.atvSDate.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.calendarViewShown = false
        datePickerDialog.datePicker.spinnersShown = true
        datePickerDialog.show()
    }

    private fun schemeType() {

        lifecycleScope.launchWhenStarted {
            jewelSoftVM.scheme(
                "5",
                "4",
                mainPreference.getCid().first()
            )
        }
        stateScheme()
    }

    private fun stateScheme() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.schemeFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {

                    }

                    is Resources.Success -> {
                        binding.atvName.dismissDropDown()
                        Log.i("TAG", "name: ${it.data}")
                        try {
                            nameList.clear()
                            idList.clear()
                            for (i in it.data!!) {
                                nameList.add(i.name)
                                idList.add(i.id)
                            }
                            val arrayAdapter = ArrayAdapter(
                                requireContext(),
                                R.layout.complete_text_view, nameList
                            )
                            binding.atvSchmeType.setAdapter(arrayAdapter)
                            binding.atvSchmeType.threshold = 1
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                }
            }
        }

        binding.atvSchmeType.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            for (i in 0 until nameList.size) {
                try {
                    if (binding.atvSchmeType.text.toString() == nameList[i]) {
                        scheme = idList[i]
                        schemeDetails(scheme)

                        Log.i("TAG", "forsalessss:$id")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }

        }
    }

    private fun schemeDetails(scheme: String) {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.enrollmentScheme(
                "11",
                mainPreference.getCid().first(),
                "2",
                scheme,
                formattedDate
            )
        }
        autoFill()
    }

    private fun autoFill() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.enrollmentSchemeFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "autoErrorFill:${it.message.toString()}")
                    }

                    is Resources.Success -> {
                        Log.i("TAG", "autoFillSchemeSuccess:${it.data} ")

                        for (i in it.data!!) {
                            binding.atvEMI.setText(i.emi)
                            binding.atvFreeEMI.setText(i.free_emi)
                            binding.atvPurity.setText(i.metal_booked_purity)
                            binding.atvEDate.setText(i.mat_date)

                        }

                    }
                }
            }
        }
    }


    private fun cusName() {
        binding.atvName.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launchWhenStarted {
                jewelSoftVM.enrollmentName(
                    "11",
                    mainPreference.getCid().first(),
                    "1",
                    text.toString()
                )
            }
            autoFillName()
        }
    }

    private fun autoFillName() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.enrollmentNameFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "autoErrorFillName:${it.message.toString()}")
                    }

                    is Resources.Success -> {
                        customerName.clear()
                        customerId.clear()
                        customerLabel.clear()
                        customerMobile.clear()

                        for (i in it.data!!) {
                            customerName.add(i.name)
                            customerId.add(i.id)
                            customerMobile.add(i.mobile)
                            customerLabel.add(i.label)
                        }
                        val arrayAdapter = ArrayAdapter(
                            requireContext(),
                            R.layout.complete_text_view, customerName
                        )
                        binding.atvName.setAdapter(arrayAdapter)
                        binding.atvName.threshold = 1
                        binding.atvName.showDropDown()
                    }
                }
            }
        }
        binding.atvName.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            for (i in 0 until customerName.size) {
                try {
                    if (binding.atvName.text.toString() == customerName[i]) {
                        custId = customerId[i]
                        schemeType()

                        Log.i("TAG", "forsalessss:$id")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun calculateTotalAmount() {
        val enteredAmount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
        val totalAmount = enteredAmount * 12
        etTotalAmount.setText(totalAmount.toInt().toString())
    }
}