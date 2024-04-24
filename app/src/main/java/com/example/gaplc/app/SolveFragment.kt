package com.example.gaplc.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.gaplc.R
import com.example.gaplc.databinding.FragmentSolveBinding


class SolveFragment : Fragment() {
    private lateinit var binding: FragmentSolveBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSolveBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
    }
    private fun initClicks(){
        binding.appCompatButton4.setOnClickListener {
            findNavController().navigate(
                SolveFragmentDirections.actionSolveFragmentToShowFirstPopulationFragment(100)
            )
        }
        binding.appCompatButton2.setOnClickListener {
            if (checkPopulation()){
                val num = binding.appCompatEditText.text.toString().toInt()
                if (binding.appCompatEditText.text?.length !=0 ||binding.appCompatEditText.text?.length !=null){
                    findNavController().navigate(
                        SolveFragmentDirections.actionSolveFragmentToShowFirstPopulationFragment(num)
                    )
                }
                else{
                    findNavController().navigate(
                        SolveFragmentDirections.actionSolveFragmentToShowFirstPopulationFragment(100)
                    )
                }
            }
            else{
                Toast.makeText(requireContext(), "where is number of population", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkPopulation(): Boolean {
        return binding.appCompatEditText.text?.length != 0
    }
}