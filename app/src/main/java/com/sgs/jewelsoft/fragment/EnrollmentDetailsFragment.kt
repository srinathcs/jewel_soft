package com.sgs.jewelsoft.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sgs.jewelsoft.MVVM.JewelSoftRepository
import com.sgs.jewelsoft.MVVM.JewelSoftViewModel
import com.sgs.jewelsoft.MVVM.JewelViewModelFactory
import com.sgs.jewelsoft.MainPreference
import com.sgs.jewelsoft.Resources
import com.sgs.jewelsoft.adapter.EnrollmentViewAdapter
import com.sgs.jewelsoft.databinding.FragmentEnrollmentDetailsBinding
import kotlinx.coroutines.flow.first

class EnrollmentDetailsFragment : Fragment() {
    private lateinit var binding: FragmentEnrollmentDetailsBinding
    private lateinit var mainPreference: MainPreference
    private lateinit var myadapter: EnrollmentViewAdapter
    private var chit = ""

    private val jewelSoftVM: JewelSoftViewModel by lazy {
        val repos = JewelSoftRepository()
        val factory = JewelViewModelFactory(repos)
        ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnrollmentDetailsBinding.inflate(inflater, container, false)
        mainPreference = MainPreference(requireContext())

        chit = requireArguments().getString("chit")!!

        viewEnrollment()



        return binding.root
    }

    private fun viewEnrollment() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.enrollmentShow(
                "8",
                "3",
                mainPreference.getCid().first(),
                chit
            )
        }
        stateView()
    }

    private fun stateView() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.enrollmentShowTwoFlow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {
                        Log.i("TAG", "stateView:${it.message} ")

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "stateView:${it.data}")
                        myadapter = EnrollmentViewAdapter(requireContext())
                        binding.rvView.adapter = myadapter
                        binding.rvView.layoutManager = LinearLayoutManager(requireContext())
                        myadapter.differ.submitList(it.data)
                    }
                }
            }
        }
    }

}