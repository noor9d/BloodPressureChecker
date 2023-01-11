package com.example.bloodpressurechecker.view

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.bloodpressurechecker.databinding.DialogWelcomeBinding
import com.example.bloodpressurechecker.databinding.FragmentMoreBinding
import com.example.bloodpressurechecker.utils.*
import com.example.bloodpressurechecker.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding.apply {
            if (requireContext().isVibrateEnable()){
                switchVibration.isChecked=true
            }
            tvRateUs.setOnClickListener{
                requireContext().appRate()
            }

            tvMoreApps.setOnClickListener{
                requireContext().appMore()
            }

            tvShare.setOnClickListener{
                requireContext().appShare()
            }

            tvHelp.setOnClickListener{
             requireActivity().infoDialog()
            }

            tvReset.setOnClickListener{
                viewmodel.resetRecord()
                requireContext().showToast("Reset Successfully")
            }

            tvReminder.setOnClickListener{
                val timePicker: TimePickerDialog = TimePickerDialog(requireActivity(), timePickerDialogListener, 12, 10, false)
                timePicker.show()
            }

            switchVibration.setOnCheckedChangeListener { _, isChecked ->
                  requireContext().addVibratePhone(isChecked)
            }


        }
    }

    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val formattedTime: String = when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        "${hourOfDay + 12} 0${minute} am"
                    } else {
                        "${hourOfDay + 12} ${minute} am"
                    }
                }
                hourOfDay > 12 -> {
                    if (minute < 10) {
                        "${hourOfDay - 12} 0${minute} pm"
                    } else {
                        "${hourOfDay - 12} ${minute} pm"
                    }
                }
                hourOfDay == 12 -> {
                    if (minute < 10) {
                        "${hourOfDay} 0${minute} pm"
                    } else {
                        "${hourOfDay} ${minute} pm"
                    }
                }
                else -> {
                    if (minute < 10) {
                        "${hourOfDay} ${minute} am"
                    } else {
                        "${hourOfDay} ${minute} am"
                    }
                }
            }


            val value=formattedTime.split(" ")
           val tp = if (value[2] == "am"){
                0
            }else{
                1
            }

            requireActivity().startAlarm(value[0].toInt(),value[1].toInt(),tp)
             requireContext().showToast("Reminder set Successfully")
           // binding.tvReminder.text =formattedTime
        }
       override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}