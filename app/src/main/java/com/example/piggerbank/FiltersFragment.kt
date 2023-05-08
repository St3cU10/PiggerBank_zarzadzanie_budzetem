package com.example.piggerbank

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*


class FiltersFragment : Fragment() {
    //val filters = arrayOf("A-Z", "Z-A", "Kwota -rosnąco", "Kwota -malejąco")


    private lateinit var textDate : TextView
    private lateinit var textDate2 : TextView
    private lateinit var buttonDate : Button
    private lateinit var buttonDate2 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }








    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filters, container, false)

        val acceptBtn: Button = view.findViewById(R.id.ok_button)
        acceptBtn.setOnClickListener {
            val fragment = HomeFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }

        buttonDate = view.findViewById(R.id.buttonDate)
        buttonDate2 = view.findViewById(R.id.buttonDate2)
        textDate = view.findViewById(R.id.textDate)
        textDate2 = view.findViewById(R.id.textDate2)


        //kalendarz pierwszy
        val calendarBox = Calendar.getInstance()
        val dateBox = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            calendarBox.set(Calendar.YEAR, year)
            calendarBox.set(Calendar.MONTH, month)
            calendarBox.set(Calendar.DAY_OF_MONTH, day)

            updateText(calendarBox)
        }

        //kalendarz drugi
        val calendarBox2 = Calendar.getInstance()
        val dateBox2 = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            calendarBox2.set(Calendar.YEAR, year)
            calendarBox2.set(Calendar.MONTH, month)
            calendarBox2.set(Calendar.DAY_OF_MONTH, day)

            updateText2(calendarBox2)
        }


        //otwieranie kalendarza pierwszego
        buttonDate.setOnClickListener{
            DatePickerDialog(view.context, dateBox, calendarBox.get(Calendar.YEAR), calendarBox.get(
                Calendar.MONTH), calendarBox.get(Calendar.DAY_OF_MONTH)).show()
        }

        //otwieranie drugiego kalendarza
        buttonDate2.setOnClickListener{
            DatePickerDialog(view.context, dateBox2, calendarBox2.get(Calendar.YEAR), calendarBox2.get(
                Calendar.MONTH), calendarBox2.get(Calendar.DAY_OF_MONTH)).show()
        }
/*
        *//*val spinner = view.findViewById<Spinner>(R.id.spinner)
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, filters)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener*//*


        val filters = arrayOf("A-Z", "Z-A", "Kwota -rosnąco", "Kwota -malejąco")
        val spinnerResult = view.findViewById<TextView>(R.id.spinnerResult)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val arrayAdapter = ArrayAdapter<String>(MainActivity(),android.R.layout.simple_spinner_dropdown_item,filters)

        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            *//*override fun onNothingSelected(p0: AdapterView<*>?) {

            }*//*

            *//*override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            spinnerResult.text
            }*//*
            *//*override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                Toast.makeText(MainActivity(),"wybrany filtr to = "+filters[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }*//*
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerResult.text = filters[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }*/




        /*// access the items of the list
        val filters = resources.getStringArray(R.array.Filters)
        // access the spinner
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                MainActivity(),
                android.R.layout.simple_spinner_item, filters
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        MainActivity(),
                        getString(R.string.selected_item) + " " +
                                "" + filters[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }*/



        return view
    }

    private fun updateText(calendar: Calendar){
        val dateFormat = "dd-MM-yyyy"
        val simple = SimpleDateFormat(dateFormat, Locale.UK)
        textDate.setText(simple.format(calendar.time))



        /*val today = Calendar.getInstance()
        println(SimpleDateFormat().format(today.time))
        println(SimpleDateFormat("dd-MM-yyyy").format(Locale.UK))*/
    }

    private fun updateText2(calendar: Calendar){
        val dateFormat = "dd-MM-yyyy"
        val simple = SimpleDateFormat(dateFormat, Locale.UK)
        textDate2.setText(simple.format(calendar.time))



        /*val today = Calendar.getInstance()
        println(SimpleDateFormat().format(today.time))
        println(SimpleDateFormat("dd-MM-yyyy").format(Locale.UK))*/
    }



    fun isValue(value : String?) : Boolean
    {
        if (value == null)
            return false

        // ZAMIENIA PRZECINKI NA KROPKI
        val valDots = value.replace(",", ".")

        // SZABLON POROWNYWANIA - TYLKO CYFRY I KROPKA
        val regex = Regex("[0-9.]+")

        return regex.matches(valDots)
    }


}