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
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.Resources
import com.sgs.jewelsoft.adapter.ReceiptViewAdapter
import com.sgs.jewelsoft.databinding.FragmentViewReceiptBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

class ViewReceiptFragment : Fragment() {
    private lateinit var binding: FragmentViewReceiptBinding
    private lateinit var jewelSoftVM: JewelSoftViewModel
    private lateinit var mainPreference: MainPreference
    private lateinit var myadapter: ReceiptViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewReceiptBinding.inflate(inflater, container, false)
        mainPreference = MainPreference(requireContext())
        viewReceipt()
        val repos = JewelSoftRepository()
        val factory = JewelViewModelFactory(repos)
        jewelSoftVM = ViewModelProvider(this, factory)[JewelSoftViewModel::class.java]
        return binding.root
    }

    private fun viewReceipt() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.viewReceipt(
                "6",
                mainPreference.getCid().first()
            )
        }
        stateViewReceipt()
    }

    private fun stateViewReceipt() {
        lifecycleScope.launchWhenStarted {
            jewelSoftVM.viewReceiptShow.collect {
                when (it) {
                    is Resources.Loading -> {

                    }

                    is Resources.Error -> {

                    }

                    is Resources.Success -> {
                        Log.i("TAG", "stateViewReceipt:${it.data} ")
                        myadapter = ReceiptViewAdapter(requireContext())
                        binding.rvView.adapter = myadapter
                        binding.rvView.layoutManager = LinearLayoutManager(requireContext())
                        myadapter.differ.submitList(it.data)

                    }
                }
            }
        }
    }


}