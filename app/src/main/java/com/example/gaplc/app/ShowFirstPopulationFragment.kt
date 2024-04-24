package com.example.gaplc.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gaplc.R
import com.example.gaplc.databinding.FragmentShowFirstPopulationBinding
import kotlinx.coroutines.delay
import kotlin.random.Random

class ShowFirstPopulationFragment : Fragment() {

    private lateinit var binding: FragmentShowFirstPopulationBinding
    private val args: ShowFirstPopulationFragmentArgs by navArgs()
    private val goalState: MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
    private val populationSize: Int = 100
    private val mutationRate: Double = 0.1
    private var bestEverState: MutableList<Int>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowFirstPopulationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        putPhotosRandomly()
        initClicks()

    }

    private fun initClicks() {
        binding.btn.setOnClickListener {
            if (args.populationNum == 0 || args.populationNum == null) {
                val array = solve(args.populationNum).first.toIntArray()
                findNavController().navigate(
                    ShowFirstPopulationFragmentDirections.actionShowFirstPopulationFragmentToFinaleFragment(
                        array,
                        solve(args.populationNum).second
                    )
                )
            } else {
                val array = solve(args.populationNum).first.toIntArray()
                val fitness = solve(args.populationNum).second
                findNavController().navigate(
                    ShowFirstPopulationFragmentDirections.actionShowFirstPopulationFragmentToFinaleFragment(
                        array,
                        fitness
                    )
                )
            }
        }
    }

    private fun putPhotosRandomly() {
        val list = generateInitialPopulation()
        val firstList = list[0]
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
        binding.img1.setImageResource(listOfImages[firstList[0]])
        binding.img2.setImageResource(listOfImages[firstList[1]])
        binding.img3.setImageResource(listOfImages[firstList[2]])
        binding.img4.setImageResource(listOfImages[firstList[3]])
        binding.img5.setImageResource(listOfImages[firstList[4]])
        binding.img6.setImageResource(listOfImages[firstList[5]])
        binding.img7.setImageResource(listOfImages[firstList[6]])
        binding.img8.setImageResource(listOfImages[firstList[7]])
        binding.img0.setImageResource(listOfImages[firstList[8]])

    }

    private fun generateInitialPopulation(): MutableList<MutableList<Int>> {
        val initialPopulation = mutableListOf<MutableList<Int>>()
        repeat(populationSize) {
            val state = goalState.toMutableList()
            state.shuffle()
            initialPopulation.add(state)
        }
        return initialPopulation
    }

    private fun fitnessFunction(state: List<Int>, goalState: List<Int>): Int {
        return state.zip(goalState).count { (i, j) -> i == j }
    }

    private fun selectParents(population: MutableList<MutableList<Int>>): Pair<MutableList<Int>, MutableList<Int>> {
        val totalFitness = population.sumByDouble { fitnessFunction(it, goalState).toDouble() }
        val randomValue1 = Random.nextDouble(0.0, totalFitness)
        val randomValue2 = Random.nextDouble(0.0, totalFitness)
        var accumulatedFitness = 0.0
        var parent1: MutableList<Int>? = null
        var parent2: MutableList<Int>? = null

        for (state in population) {
            accumulatedFitness += fitnessFunction(state, goalState)
            if (accumulatedFitness >= randomValue1 && parent1 == null) {
                parent1 = state
            }
            if (accumulatedFitness >= randomValue2 && parent2 == null) {
                parent2 = state
            }
            if (parent1 != null && parent2 != null) {
                break
            }
        }

        return parent1!! to parent2!!
    }


    private fun crossover(
        parent1: MutableList<Int>,
        parent2: MutableList<Int>
    ): Pair<MutableList<Int>, MutableList<Int>> {
        val crossoverPoint = Random.nextInt(1, parent1.size)
        val child1 =
            parent1.subList(0, crossoverPoint) + parent2.subList(crossoverPoint, parent2.size)
        val child2 =
            parent2.subList(0, crossoverPoint) + parent1.subList(crossoverPoint, parent1.size)
        return child1.toMutableList() to child2.toMutableList()
    }

    private fun mutate(state: MutableList<Int>): MutableList<Int> {
        if (Random.nextDouble() < mutationRate) {
            val (idx1, idx2) = List(2) { Random.nextInt(state.size) }
            state[idx1] = state[idx2].also { state[idx2] = state[idx1] }
        }
        return state
    }

    private fun evolvePopulation(population: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        val newPopulation = mutableListOf<MutableList<Int>>()
        while (newPopulation.size < populationSize) {
            val (parent1, parent2) = selectParents(population)
            var (child1, child2) = crossover(parent1, parent2)
            child1 = mutate(child1)
            child2 = mutate(child2)
            newPopulation.add(child1)
            newPopulation.add(child2)
        }
        return newPopulation
    }

    private fun solve(numGenerations: Int = 100): Pair<MutableList<Int>, Int> {
        var population = generateInitialPopulation()
        var bestState: MutableList<Int>? = null
        for (generation in 1..numGenerations) {
            population = evolvePopulation(population)
            bestState = population.maxByOrNull { fitnessFunction(it, goalState) }
            if (bestState != null) {
                val bestFitness = fitnessFunction(bestState, goalState)
                if (bestFitness == goalState.size) {
                    return bestState to generation
                }
                if (bestEverState == null || fitnessFunction(
                        bestState,
                        goalState
                    ) > fitnessFunction(bestEverState!!, goalState)
                ) {
                    bestEverState = bestState
                }
//                println("Generation $generation: Best state: $bestState, Fitness: $bestFitness")
            }
        }
        return bestEverState!! to numGenerations
    }

}

