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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.sgs.jewelsoft.MVVM.JewelSoftRepository
import com.sgs.jewelsoft.MVVM.JewelSoftViewModel
import com.sgs.jewelsoft.MVVM.JewelViewModelFactory
import com.sgs.jewelsoft.MainPreference
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.Resources
import com.sgs.jewelsoft.adapter.EnrollmentAdapter
import com.sgs.jewelsoft.databinding.FragmentEnrollmentBinding
import kotlinx.coroutines.flow.first

class EnrollmentFragment : Fragment() {
    private lateinit var binding: FragmentEnrollmentBinding
    private lateinit var myadapter: EnrollmentAdapter
    private val jewelSoftVM: JewelSoftViewModel by lazy {
        val repos = JewelSoftRepository()
        val factory = JewelViewModelFactory(repos)
        ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]
    }
    private lateinit var mainPreference: MainPreference
    private var chitList: MutableList<String> = mutableListOf()
    private var idList: MutableList<String> = mutableListOf()
    private var valueList = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainPreference = MainPreference(requireContext())
        binding = FragmentEnrollmentBinding.inflate(inflater, container, false)
        enrollment()

        binding.btnSearch.setOnClickListener {
            binding.atvChitID.dismissDropDown()
            viewDetail()
        }

        return binding.root
    }

    private fun viewDetail() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.enrollmentTwo(
                "8",
                "2",
                mainPreference.getCid().first(),
                valueList
            )
        }
        stateView()
    }

    private fun stateView() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.enrollmentTwoFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {

                    }

                    is Resources.Success -> {
                        binding.atvChitID.dismissDropDown()
                        try {
                            Log.i("TAG", "stateView: ${it.data}")
                            myadapter = EnrollmentAdapter(requireContext())
                            binding.rvView.adapter = myadapter
                            binding.rvView.layoutManager = LinearLayoutManager(requireContext())
                            myadapter.differ.submitList(it.data)

                            myadapter.dashboardListener = {
                                val bundle  = Bundle()
                                bundle.putString("chit",it.chit_id)
                                Navigation.findNavController(requireView()).navigate(R.id.enrollmentDetailsFragment,bundle)
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                }
            }
        }
    }

    private fun enrollment() {
        binding.atvChitID.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launchWhenStarted {
                jewelSoftVM.enrollment(
                    "8",
                    "1",
                    mainPreference.getCid().first(),
                    text.toString()
                    )
            }
            stateEnrollment()
        }
    }

    private fun stateEnrollment() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.enrollmentFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "stateEnrollment:${it.message} ")

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "stateEnrollment:${it.data}")
                        try {
                            chitList.clear()
                            idList.clear()
                            for (i in it.data!!) {
                                chitList.add(i.label)
                                idList.add(i.chit_id)
                            }
                            val arrayAdapter = ArrayAdapter(
                                requireContext(),
                                R.layout.complete_text_view, chitList
                            )
                            binding.atvChitID.setAdapter(arrayAdapter)
                            binding.atvChitID.threshold = 1
                            binding.atvChitID.showDropDown()

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        binding.atvChitID.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            for (i in 0 until chitList.size) {
                try {
                    if (binding.atvChitID.text.toString() == chitList[i]) {
                        valueList = idList[i]
                        viewDetail()
                        Log.i("TAG", "atvAccount:$idList")
                    }
                } catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }
            }
            binding.atvChitID.dismissDropDown()
        }
    }
}