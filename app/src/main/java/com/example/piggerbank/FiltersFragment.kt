package com.example.piggerbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piggerbank.Baza.MoneyDB
import com.example.piggerbank.RecycleView.CategoryRV
import com.example.piggerbank.RecycleView.MyAdapterCategory


class FiltersFragment : Fragment() {
    //val filters = arrayOf("A-Z", "Z-A", "Kwota -rosnąco", "Kwota -malejąco")

    private lateinit var moneyDB: MoneyDB
    private lateinit var adapterRV : MyAdapterCategory
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryArrayyList : ArrayList<CategoryRV>

    lateinit var categoryRVname : Array<String>
    lateinit var categoryRVupper : Array<String>
    lateinit var categoryRVid : Array<Int>








    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filters, container, false)

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


        // ARTURA - ZAKOMENTOWALEM BO COS KRZYCZALO

  /*      // access the items of the list
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
*/
        // RECYCLER VIEW
        moneyDB = MoneyDB.getInstance(MainActivity())

        categoryInitialize(null)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapterRV = MyAdapterCategory(categoryArrayyList)
        recyclerView.adapter = adapterRV


        // SZUKANIE GLEBIEJ
        adapterRV.setOnItemClickListener(object : MyAdapterCategory.onItemClickListener{
            override fun onItemClick(position: Int){
                val id = categoryArrayyList[position].id
                //val upper : Int? = moneyDB.moneyDao().getOneUpperCategory(id)
                categoryInitialize(id)
                adapterRV.updateData(categoryArrayyList)
                adapterRV.notifyDataSetChanged()
            }
        })

        // AKCEPTACJA FILTROW
        val acceptBtn: Button = view.findViewById(R.id.ok_button)
        acceptBtn.setOnClickListener {
            var catUpper : Int? = null
            if (categoryArrayyList.size > 0) {
                val catId = categoryArrayyList[0].id
                catUpper = moneyDB.moneyDao().getOneUpperCategory(catId)
            }
            val fragment = HomeFragment(catUpper)
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }




        return view
    }

    private fun categoryInitialize(upper : Int?) {
        categoryArrayyList = arrayListOf<CategoryRV>()

        if (upper == null)
        {
            categoryRVid = arrayOf<Int>(1, 2)
            categoryRVname = arrayOf<String>("PRZYCHODY", "WYDATKI")
            categoryRVupper = moneyDB.moneyDao().getAllUpperCategory()

            for (i in categoryRVid.indices)
            {
                val categoryData = CategoryRV(
                    categoryRVid[i],
                    categoryRVname[i],
                    categoryRVupper[i]
                )
                categoryArrayyList.add(categoryData)
            }
            return
        }

        categoryRVid = moneyDB.moneyDao().getAllCategoryIdWhereUpper(upper)
        categoryRVname = moneyDB.moneyDao().getAllCategoryNameWhereUpper(upper)
        categoryRVupper = moneyDB.moneyDao().getAllUpperCategoryWhereUpper(upper)

        for (i in categoryRVid.indices)
        {
            val categoryData = CategoryRV(
                categoryRVid[i],
                categoryRVname[i],
                categoryRVupper[i]
            )
            categoryArrayyList.add(categoryData)
        }
    }


}