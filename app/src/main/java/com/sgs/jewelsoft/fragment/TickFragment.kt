package com.sgs.jewelsoft.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.databinding.FragmentTickBinding

class TickFragment : Fragment() {
    private lateinit var binding: FragmentTickBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentTickBinding.inflate(inflater, container, false)
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        return binding.root
    }

}