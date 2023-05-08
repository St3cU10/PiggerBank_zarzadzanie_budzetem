package com.example.piggerbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class FiltersFragment : Fragment() {
    //val filters = arrayOf("A-Z", "Z-A", "Kwota -rosnąco", "Kwota -malejąco")

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
        // access the items of the list
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
        }



        return view
    }


}