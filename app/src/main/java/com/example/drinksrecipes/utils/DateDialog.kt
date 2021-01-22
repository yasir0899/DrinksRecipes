package com.example.providerportal.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.providerportal.OnDateSetCallback

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class  DateDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var picker: DatePickerDialog
    var value: String? = null
    var dob: String? = null
    var DobMin: String? = null

    var onDateSetCallback: OnDateSetCallback? = null

   

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        value = requireArguments().getString("Calender")
        dob = requireArguments().getString("Dob")
        DobMin = requireArguments().getString("DobMin")
        // Use the current date as the default date in the dialog
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        // Create a new instance of DatePickerDialog and return it
        picker = DatePickerDialog(requireActivity(),this, year, month, day)

        if (value == "true") {
            picker
        } else if (DobMin == "true" && dob == "true") {
            picker.datePicker.maxDate = System.currentTimeMillis()
        } else {
            picker.datePicker.minDate = System.currentTimeMillis()
        }
        return picker
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        onDateSetCallback?.onDateSet("${month + 1}/$dayOfMonth/$year")

    }

    fun setCallback(onDateSetCallback: OnDateSetCallback) {
        this.onDateSetCallback = onDateSetCallback
    }

     fun dateFun(): String? {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Calendar.getInstance().time)
    }

     fun dateAfter(date: String): Boolean? {

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date1 = sdf.parse(date)
        val date2 = sdf.parse(dateFun())
        System.out.println("Date1 is after Date2")
         return date1.after(date2)
    }

     fun dateBefore(date: String): Boolean? {

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date1 = sdf.parse(date)
        val date2 = sdf.parse(dateFun())
        return date1.before(date2)
    }
      fun getCurrentTimeInLong(): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Calendar.getInstance().time).toLong()
    }

       fun String.toLong(): Long {
        val f = SimpleDateFormat("yyyy-MM-dd")
        try {
            val d = f.parse(this)
            return d!!.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0L
    }



}