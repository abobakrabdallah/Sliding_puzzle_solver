package com.example.gaplc.app.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gaplc.R
import com.example.gaplc.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    private lateinit var binding:FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding= FragmentStartBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
    }

    private fun initClick(){
        binding.appCompatButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_solveFragment)
        }
    }
}