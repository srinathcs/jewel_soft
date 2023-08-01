package com.sgs.jewelsoft.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import java.text.SimpleDateFormat
import java.util.Calendar

class ReceiptEntryFragment : Fragment() {
    private lateinit var binding: FragmentRecepitEntryBinding
    private lateinit var jewelSoftVM: JewelSoftViewModel
    private lateinit var mainPreference: MainPreference
    private var id = ""
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainPreference = MainPreference(requireContext())
        binding = FragmentRecepitEntryBinding.inflate(inflater, container, false)
        val currentCalender = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = simpleDateFormat.format(currentCalender.time)
        binding.tvDate.text = currentDate

        receiptType()
        paymentType()
        accountType()
        cusName()

        val repos = JewelSoftRepository()
        val factory = JewelViewModelFactory(repos)
        jewelSoftVM = ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]
        return binding.root

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
                                valueList.add(i.value)
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
                        id = valueList[i]
                        Log.i("TAG", "forsalessss:$id")
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
                        id = paymentList[i]
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
                        id = idList[i]
                        Log.i("TAG", "forsalessss:$id")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun cusName() {
        binding.atvName.doOnTextChanged { text, _, _, _ ->
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
                            customerName.add(i.label)
                            customerValues.add(i.value)
                            customerChitId.add(i.chit_id)
                        }
                        val arrayAdapter = ArrayAdapter(
                            requireContext(),
                            R.layout.complete_text_view, customerName
                        )
                        binding.atvName.setAdapter(arrayAdapter)
                        binding.atvName.threshold = 1

                    }
                }
            }
        }

        binding.atvName.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            for (i in 0 until customerName.size) {
                try {
                    if (binding.atvName.text.toString() == customerName[i]) {
                        customerNameId = customerValues[i]
                        customerChitIdString = customerChitId[i]

                        balance()

                        binding.atvChitID.setText(customerChitIdString)

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
                customerNameId,
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
                        Log.i("TAG", "getSuccessBalance:${it.data}")

                        for (i in it.data!!) {
                            binding.atvBalance.setText(i.balance)
                        }
                    }
                }
            }
        }
    }

}