package com.sgs.jewelsoft.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sgs.jewelsoft.R
import com.sgs.jewelsoft.databinding.FragmentViewReceiptBinding

class ViewReceiptFragment : Fragment() {
    private lateinit var binding: FragmentViewReceiptBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }


}