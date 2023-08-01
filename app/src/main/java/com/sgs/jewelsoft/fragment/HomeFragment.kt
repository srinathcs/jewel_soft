package com.sgs.jewelsoft.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sgs.jewelsoft.Location.FusedLocationService
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var lt =""
    private var ln =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        FusedLocationService.latitudeFlow.observe(requireActivity()) {
            lt = it.latitude.toString()
            ln = it.longitude.toString()
            Log.i("TAG", "onCreateL:$lt")
            Log.i("TAG", "onCreateLo:$ln")

        }

        binding.cvView1.setOnClickListener {
            findNavController().navigate(R.id.entryFragment)
        }

        binding.cvViewEntryReceipt.setOnClickListener {
            findNavController().navigate(R.id.entryReceiptFragment)
        }
        return binding.root
    }

}