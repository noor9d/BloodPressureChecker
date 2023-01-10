package com.example.bloodpressurechecker.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.example.bloodpressurechecker.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var pickerSystolic: NumberPicker
    private lateinit var pickerDiastolic: NumberPicker
    private lateinit var pickerPulse: NumberPicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSystolicPicker()
        setupDiastolicPicker()
        setupPulsePicker()
    }

    private fun setupSystolicPicker() {
        pickerSystolic = binding.numberPickerSystolic
        pickerSystolic.maxValue = 4
        pickerSystolic.minValue = 0
        val pickerValues = arrayOf("dog", "cat", "lizard", "turtle", "axolotl")
        pickerSystolic.displayedValues = pickerValues

        pickerSystolic.setOnValueChangedListener { _, _, _ ->
            val valuePicker1: Int = pickerSystolic.value
            Log.d("picker value", pickerValues[valuePicker1])
        }
    }

    private fun setupDiastolicPicker() {
        pickerDiastolic = binding.numberPickerDiastolic
        pickerDiastolic.maxValue = 4
        pickerDiastolic.minValue = 0
        val pickerValues = arrayOf("dog", "cat", "lizard", "turtle", "axolotl")
        pickerDiastolic.displayedValues = pickerValues

        pickerDiastolic.setOnValueChangedListener { _, _, _ ->
            val valuePicker1: Int = pickerDiastolic.value
            Log.d("picker value", pickerValues[valuePicker1])
        }
    }

    private fun setupPulsePicker() {
        pickerPulse = binding.numberPickerPulse
        pickerPulse.maxValue = 4
        pickerPulse.minValue = 0
        val pickerValues = arrayOf("dog", "cat", "lizard", "turtle", "axolotl")
        pickerPulse.displayedValues = pickerValues

        pickerPulse.setOnValueChangedListener { _, _, _ ->
            val valuePicker1: Int = pickerPulse.value
            Log.d("picker value", pickerValues[valuePicker1])
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}