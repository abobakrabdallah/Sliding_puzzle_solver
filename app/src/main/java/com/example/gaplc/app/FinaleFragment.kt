package com.example.gaplc.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gaplc.R
import com.example.gaplc.databinding.FragmentFinaleBinding


class FinaleFragment : Fragment() {
    private lateinit var binding:FragmentFinaleBinding
    private val args: FinaleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinaleBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextsAndImages()
        initClicks()

    }

    private fun initClicks(){
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_finaleFragment_to_solveFragment)
        }
    }
    private fun setTextsAndImages(){
        val list =args.list
        binding.tvSolution.text=list.joinToString(separator = ",")
        binding.tvNumber.text =args.generationNum.toString()
        val firstList = args.list
        val listOfImages = mutableListOf(
            R.drawable.image_part_001,
            R.drawable.image_part_002,
            R.drawable.image_part_003,
            R.drawable.image_part_004,
            R.drawable.image_part_005,
            R.drawable.image_part_006,
            R.drawable.image_part_007,
            R.drawable.image_part_008,
            R.drawable.img_0
        )
        binding.img1.setImageResource(listOfImages[firstList[0]]-1)
        binding.img2.setImageResource(listOfImages[firstList[1]]-1)
        binding.img3.setImageResource(listOfImages[firstList[2]]-1)
        binding.img4.setImageResource(listOfImages[firstList[3]]-1)
        binding.img5.setImageResource(listOfImages[firstList[4]]-1)
        binding.img6.setImageResource(listOfImages[firstList[5]]-1)
        binding.img7.setImageResource(listOfImages[firstList[6]]-1)
        binding.img9.setImageResource(R.drawable.img_0)
    }


}