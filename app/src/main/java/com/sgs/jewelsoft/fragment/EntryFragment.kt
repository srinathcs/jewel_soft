package com.sgs.jewelsoft.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sgs.jewelsoft.MVVM.JewelSoftRepository
import com.sgs.jewelsoft.MVVM.JewelSoftViewModel
import com.sgs.jewelsoft.MVVM.JewelViewModelFactory
import com.sgs.jewelsoft.MainPreference
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.Resources
import com.sgs.jewelsoft.databinding.FragmentEntryBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EntryFragment : Fragment(R.layout.fragment_entry) {
    private lateinit var binding: FragmentEntryBinding
    private val jewelSoftVM: JewelSoftViewModel by lazy {
        val repos = JewelSoftRepository()
        val factory = JewelViewModelFactory(repos)
        ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]
    }
    private lateinit var mainPreference: MainPreference
    private var nameList: MutableList<String> = mutableListOf()
    private var idList: MutableList<String> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEntryBinding.bind(view)

        mainPreference = MainPreference(requireContext())

        schemeType()

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
    }

    private fun saveData() {
        when {
            binding.etName.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter a name", Toast.LENGTH_SHORT).show()
            }

            binding.etDob.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter the DOB", Toast.LENGTH_SHORT).show()
            }

            binding.etMobile.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter the Mobile Number", Toast.LENGTH_SHORT)
                    .show()
            }

            binding.etAnniversary.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter the Anniversary Date", Toast.LENGTH_SHORT)
                    .show()
            }

            binding.etEmail.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter the Email", Toast.LENGTH_SHORT).show()
            }

            binding.atvSchmeType.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Select the Scheme", Toast.LENGTH_SHORT).show()
            }

            binding.etPanNum.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter the Pan Number", Toast.LENGTH_SHORT).show()
            }

            binding.etAadharNum.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter the Aadhar Number", Toast.LENGTH_SHORT)
                    .show()
            }

            binding.etPresentAddress.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter the Present Address", Toast.LENGTH_SHORT)
                    .show()
            }

            binding.etPrementAddress.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Enter the Permanent Address", Toast.LENGTH_SHORT)
                    .show()
            }

            (binding.etMobile.text.length < 10) -> {
                Toast.makeText(
                    requireContext(),
                    "PAN card should have at least 10 digits",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            (binding.etPanNum.text.length < 10) -> {
                Toast.makeText(
                    requireContext(),
                    "PAN card should have at least 10 digits",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            (binding.etAadharNum.text.length < 12) -> {
                Toast.makeText(
                    requireContext(),
                    "Aadhar card should have at least 12 digits",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }


            else -> {
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
                        binding.etAadharNum.text.toString(),
                        binding.atvSchmeType.text.toString()
                    )
                    validationSuccess()
                }
            }
        }
    }

    private fun validationSuccess() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.testSuccessFlow.collect {
                when (it) {
                    is Resources.Error -> {
                        Log.i("TAG", "validate_for_error: ${it.message.toString()}")
                        Toast.makeText(requireContext(), "Check the internet", Toast.LENGTH_SHORT).show()
                        binding.progress.visibility = View.VISIBLE
                        binding.btnSave.visibility = View.GONE
                        delay(1000)
                        binding.progress.visibility = View.GONE
                        binding.btnSave.visibility = View.VISIBLE

                    }

                    is Resources.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                        binding.btnSave.visibility = View.GONE
                    }

                    is Resources.Success -> {
                        Log.i("TAG", "validationSuccess: ${it.data}")

                        binding.progress.visibility = View.GONE
                        binding.btnSave.visibility = View.VISIBLE

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
                                findNavController().navigate(R.id.tickFragment)

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
