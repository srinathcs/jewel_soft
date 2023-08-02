package com.sgs.jewelsoft.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sgs.jewelsoft.MVVM.JewelSoftRepository
import com.sgs.jewelsoft.MVVM.JewelSoftViewModel
import com.sgs.jewelsoft.MVVM.JewelViewModelFactory
import com.sgs.jewelsoft.MainPreference
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.Resources
import com.sgs.jewelsoft.databinding.FragmentRecepitEntryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

class ReceiptEntryFragment : Fragment() {
    private lateinit var binding: FragmentRecepitEntryBinding
    private lateinit var jewelSoftVM: JewelSoftViewModel
    private lateinit var mainPreference: MainPreference
    private var payId = ""
    private var accId = ""
    private var receiptId = ""
    private var paymentType: MutableList<String> = mutableListOf()
    private var paymentList: MutableList<String> = mutableListOf()
    private var nameList: MutableList<String> = mutableListOf()

    private var idList: MutableList<String> = mutableListOf()
    private var accList: MutableList<String> = mutableListOf()
    private var valueList: MutableList<String> = mutableListOf()

    private var customerNameId = ""
    private var customerChitIdString = ""
    private var customerName: MutableList<String> = mutableListOf()
    private var customerValues: MutableList<String> = mutableListOf()
    private var customerChitId: MutableList<String> = mutableListOf()

    private var currentDate = ""
    private var purity = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainPreference = MainPreference(requireContext())
        binding = FragmentRecepitEntryBinding.inflate(inflater, container, false)

        val currentCalender = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        currentDate = simpleDateFormat.format(currentCalender.time)
        binding.tvDate.text = currentDate

        receiptType()
        paymentType()
        accountType()
        cusName()
        binding.btnSave.setOnClickListener {
            saveData()
        }
        val repos = JewelSoftRepository()
        val factory = JewelViewModelFactory(repos)
        jewelSoftVM = ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]
        return binding.root

    }

    private fun saveData() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.saveReceipt(
                "3",
                mainPreference.getCid().first(),
                mainPreference.getUserId().first(),
                currentDate,
                binding.atvName.text.toString(),
                customerNameId,
                binding.atvChitID.text.toString(),
                binding.atvBalance.text.toString(),
                payId,
                binding.etRemark.text.toString(),
                accId,
                "",
                purity,
                binding.etAmount.text.toString()
            )
        }
        saveRecepit()
    }

    private fun saveRecepit() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.saveReceiptFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "saveErrorRecepit:${it.message.toString()}")
                    }

                    is Resources.Success -> {
                        Log.i("TAG", "validationSuccess: ${it.data}")
                        if (it.data!!.error == false) {
                            binding.atvName.setText("")
                            binding.atvChitID.setText("")
                            binding.atvAccount.setText("")
                            binding.atvBalance.setText("")
                            binding.atvReceiptType.setText("")
                            binding.atvPaymentType.setText("")
                            binding.etAmount.setText("")
                            binding.etRemark.setText("")
                            Toast.makeText(
                                requireContext(),
                                "Data Added Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
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

    private fun accountType() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.accountType(
                "3",
                "5",
                mainPreference.getCid().first()
            )
        }
        stateAccount()
    }

    private fun stateAccount() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.testAccountTypeFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "setVehicleError: ${it.data}")

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "setVehicle: ${it.data}")
                        try {
                            accList.clear()
                            valueList.clear()
                            for (i in it.data!!) {
                                accList.add(i.name)
                                valueList.add(i.id)
                            }
                            val arrayAdapter = ArrayAdapter(
                                requireContext(),
                                R.layout.complete_text_view, accList
                            )
                            binding.atvAccount.setAdapter(arrayAdapter)
                            binding.atvAccount.threshold = 1
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        binding.atvAccount.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            for (i in 0 until nameList.size) {
                try {
                    if (binding.atvAccount.text.toString() == accList[i]) {
                        accId = valueList[i]
                        Log.i("TAG", "atvAccount:$accId")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun paymentType() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.paymentType(
                "2",
                "5",
                mainPreference.getCid().first()
            )
        }
        statePayment()
    }

    private fun statePayment() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.testPaymentTypeFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "setVehicleError: ${it.data}")

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "setVehicle: ${it.data}")
                        try {
                            paymentType.clear()
                            paymentList.clear()
                            for (i in it.data!!) {
                                paymentType.add(i.name)
                                paymentList.add(i.value)
                            }
                            val arrayAdapter = ArrayAdapter(
                                requireContext(),
                                R.layout.complete_text_view, paymentType
                            )
                            binding.atvPaymentType.setAdapter(arrayAdapter)
                            binding.atvPaymentType.threshold = 1
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        binding.atvPaymentType.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            for (i in 0 until paymentType.size) {
                try {
                    if (binding.atvPaymentType.text.toString() == paymentType[i]) {
                        payId = paymentList[i]
                        Log.i("TAG", "forsalessss:$id")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun receiptType() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.receiptType(
                "1",
                "5",
                mainPreference.getCid().first()
            )
        }
        state()
    }

    private fun state() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.testEntryReceiptFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "setVehicleError: ${it.data}")

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "setVehicle: ${it.data}")
                        try {
                            nameList.clear()
                            idList.clear()
                            for (i in it.data!!) {
                                nameList.add(i.name)
                                idList.add(i.value)
                            }
                            val arrayAdapter = ArrayAdapter(
                                requireContext(),
                                R.layout.complete_text_view, nameList
                            )
                            binding.atvReceiptType.setAdapter(arrayAdapter)
                            binding.atvReceiptType.threshold = 1

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        binding.atvReceiptType.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            for (i in 0 until nameList.size) {
                try {
                    if (binding.atvReceiptType.text.toString() == nameList[i]) {
                        receiptId = idList[i]
                        Log.i("TAG", "forsalessss:$id")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun cusName() {
        binding.atvChitID.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launchWhenStarted {
                jewelSoftVM.autoFillName(
                    "1",
                    "4",
                    mainPreference.getCid().first(),
                    text.toString()
                )
                Log.i("TAG", "cusName:${text.toString()}")
            }
            stateAutoFillName()
        }

    }

    private fun stateAutoFillName() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.testAutoFillNameFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "setVehicleError: ${it.data}")
                    }

                    is Resources.Success -> {

                        customerName.clear()
                        customerValues.clear()
                        customerChitId.clear()

                        for (i in it.data!!) {
                            customerName.add(i.chit_id)
                            customerValues.add(i.id)
                            customerChitId.add(i.label)
                        }
                        val arrayAdapter = ArrayAdapter(
                            requireContext(),
                            R.layout.complete_text_view, customerName
                        )
                        binding.atvChitID.setAdapter(arrayAdapter)
                        binding.atvChitID.threshold = 1
                        binding.atvChitID.showDropDown()

                    }
                }
            }
        }

        binding.atvChitID.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            for (i in 0 until customerName.size) {
                try {
                    if (binding.atvChitID.text.toString() == customerName[i]) {
                        customerNameId = customerValues[i]
                        customerChitIdString = customerChitId[i]

                        balance()

                        binding.atvName.setText(customerChitIdString)
                        binding.atvChitID.dismissDropDown()

                        Log.i("TAG", "forsalessss:$id")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }

        }
    }

    private fun balance() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.balance(
                "2",
                "4",
                mainPreference.getCid().first(),
                customerChitIdString,
                binding.atvChitID.text.toString()
            )
        }
        getBalance()
    }

    private fun getBalance() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.balanceShow.collect {
                when (it) {
                    is Resources.Loading -> {
                        Log.i("TAG", "getLoadingBalance: ${it.message.toString()}")
                    }

                    is Resources.Error -> {
                        Log.i("TAG", "getBalanceError:${it.message.toString()}")
                    }

                    is Resources.Success -> {
                        binding.atvChitID.dismissDropDown()

                        Log.i("TAG", "getSuccessBalance:${it.data}")

                        for (i in it.data!!) {
                            binding.atvBalance.setText(i.balance)

                            for (j in i.rate) {

                                purity = j.purity

                            }
                        }
                    }
                }
            }
        }
    }
}