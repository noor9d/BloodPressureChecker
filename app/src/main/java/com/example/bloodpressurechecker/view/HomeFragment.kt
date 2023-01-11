package com.example.bloodpressurechecker.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker.OnValueChangeListener
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bloodpressurechecker.R
import com.example.bloodpressurechecker.data.remote.BloodPressureInfo
import com.example.bloodpressurechecker.databinding.FragmentHomeBinding
import com.example.bloodpressurechecker.utils.*
import com.example.bloodpressurechecker.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        binding.btnDate.text= getCurrentDate()
        binding.btnTime.text= getCurrentTime()
         setNumberPickerValue()

        viewmodel.apply {
            _bloodInfo.observe(requireActivity()) {
            }
        }

        binding.apply {
            btnDate.setOnClickListener{
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(requireActivity(), { view,year, monthOfYear, dayOfMonth ->

                    c.set(year,monthOfYear,dayOfMonth)
                    val mt = SimpleDateFormat("MMMM d,yyyy")
                        btnDate.text = mt.format(c.time)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }

            btnTime.setOnClickListener{
                val timePicker: TimePickerDialog = TimePickerDialog(requireActivity(), timePickerDialogListener, 12, 10, false)
                timePicker.show()
            }

            btnSave.setOnClickListener{
                if (requireContext().isVibrateEnable()){
                    requireContext().vibratePhone()
                }

                binding.apply {
                    viewmodel.insertData(BloodPressureInfo(
                        0,
                        numberPickerSystolic.value,
                        numberPickerDiastolic.value,
                        numberPickerPulse.value,
                        btnDate.text.toString(),
                        btnTime.text.toString(),
                        getStageName(checkStage(numberPickerSystolic.value,numberPickerDiastolic.value))))
                }

                requireContext().showToast("Saved")
            }

            numberPickerSystolic.setOnValueChangedListener(OnValueChangeListener { numberPicker, i, i1 ->
                val valueSystolic: Int = numberPickerSystolic.value
                requireContext().logShow("Systolic Picker :$valueSystolic")
                val stage=checkStage(numberPickerSystolic.value,numberPickerDiastolic.value)
                tvHypotension.text=getStageName(stage)
                tvInfo.text=getStageValue(stage)
            })
            numberPickerDiastolic.setOnValueChangedListener(OnValueChangeListener { numberPicker, i, i1 ->
                val valueSystolic: Int = numberPickerSystolic.value
                requireContext().logShow("Systolic Picker :$valueSystolic")
                val stage=checkStage(numberPickerSystolic.value,numberPickerDiastolic.value)
                tvHypotension.text=getStageName(stage)
                tvInfo.text=getStageValue(stage)
            })
        }

    }

    private fun setNumberPickerValue(){
        binding.apply {
           numberPickerDiastolic.maxValue=300
           numberPickerDiastolic.minValue=40

           numberPickerSystolic.maxValue=300
           numberPickerSystolic.minValue=40

           numberPickerPulse.maxValue=300
           numberPickerPulse.minValue=30
        }
    }

    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            val formattedTime: String = when {
                hourOfDay == 0 -> {
                    if (minute < 10) {
                        "${hourOfDay + 12}:0${minute} am"
                    } else {
                        "${hourOfDay + 12}:${minute} am"
                    }
                }
                hourOfDay > 12 -> {
                    if (minute < 10) {
                        "${hourOfDay - 12}:0${minute} pm"
                    } else {
                        "${hourOfDay - 12}:${minute} pm"
                    }
                }
                hourOfDay == 12 -> {
                    if (minute < 10) {
                        "${hourOfDay}:0${minute} pm"
                    } else {
                        "${hourOfDay}:${minute} pm"
                    }
                }
                else -> {
                    if (minute < 10) {
                        "${hourOfDay}:${minute} am"
                    } else {
                        "${hourOfDay}:${minute} am"
                    }
                }
            }

            binding.btnTime.text = formattedTime
        }

    fun getStageName(stage: Int): String {
        var st=""
        when (stage) {
            1 -> {
                st= "Hypotension"
            }
            2 -> {
                st="Normal"
            }
            3 -> {
                st= "Elevated"
            }
            4 -> {
                st= "Hypertension_stage_1"
            }
            5 -> {
                st= "Hypertension_stage_2"
            }
            6 -> {
                st=  "Hypertensive"
            }
            else -> {
                st="Hypotension"
            }
        }
        return st
    }

    fun getStageValue(stage: Int): String {
        var st=""
        when (stage) {
            1 -> {
                st= resources.getString(R.string.stageHypotension)
            }
            2 -> {
                st= resources.getString(R.string.stageNormal)
            }
            3 -> {
                st= resources.getString(R.string.stageElevated)
            }
            4 -> {
                st= resources.getString(R.string.stageHypertensionStageOne)
            }
            5 -> {
                st= resources.getString(R.string.stageHypertensionStageTwo)
            }
            6 -> {
                st= resources.getString(R.string.stageHypertensive)
            }
            else -> {
                st= resources.getString(R.string.stageHypotension)
            }
        }
        return st
    }

    fun checkStage(sysValue: Int, diaValue: Int): Int {
        var stage = 1
        //Hypotension
        if (sysValue < 130 || diaValue < 60) {
            stage = 1
        }

        // Normal
        if (sysValue in 90..119 &&
            diaValue >= 60 && diaValue <= 79
        ) {
            stage = 2
        }

        //Elevated
        if (sysValue in 120..129 &&
            diaValue >= 60 && diaValue <= 79
        ) {
            stage = 3
        }

        //Hypertension Stage 1
        if (sysValue in 130..139||
            diaValue in 80..89
        ) {
            stage = 4
        }

        //Hypertension Stage 2
        if (sysValue in 140..180 ||
            diaValue in 90..120
        ) {
            stage = 5
        }

        //Hypertensive
        if (sysValue >= 180 || diaValue > 120) {
            stage = 6
        }
        return stage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}