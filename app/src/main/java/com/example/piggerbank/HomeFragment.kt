package com.example.piggerbank

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piggerbank.Baza.MoneyDB

import androidx.recyclerview.widget.RecyclerView
import com.example.piggerbank.Baza.Money
import com.example.piggerbank.RecycleView.MoneyRV
import com.example.piggerbank.RecycleView.MyAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class HomeFragment(val upperIdInitialize : Int? = null) : Fragment() {

    private lateinit var moneyDB: MoneyDB
    private lateinit var categoriesList: List<String>
    lateinit var dropmenu: AutoCompleteTextView
    lateinit var editTextKwota: EditText
    lateinit var editTextOpis: EditText

    private lateinit var adapter: MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var moneyArrayList : ArrayList<MoneyRV>

    lateinit var moneyRVid : Array<Int>
    lateinit var moneyRVname : Array<String>
    lateinit var moneyRVvalue : Array<Double>
    lateinit var moneyRVcat : Array<String>
    lateinit var moneyRVdate : Array<Date>

    lateinit var moneyRV : List<Money>




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val nextBtn : Button = view.findViewById(R.id.addMoney)
        nextBtn.setOnClickListener{
            val fragment = AddMoneyFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }


        val filtersBtn : ImageButton = view.findViewById(R.id.filters_button)
        filtersBtn.setOnClickListener{
            val fragment = FiltersFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }

        moneyDB = MoneyDB.getInstance(MainActivity())

            // RECYCLER VIEW
        moneyInitialize(upperIdInitialize)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapter(moneyArrayList)
        recyclerView.adapter = adapter


        adapter.setOnItemCLickListener(object : MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                val id = moneyArrayList[position].id
                //Toast.makeText(view.context, "$id", Toast.LENGTH_SHORT).show()

                // ZAMIAST PRZENOSZENIA, moneyInitialize(category)

                val fragment = EditMoneyFragment(id)
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container,fragment)?.commit()
            }


        })







        // STARSZA WERSJA - DZIALA
/*
    // FUNKCJE LAYOUTA
        moneyDB = MoneyDB.getInstance(MainActivity())
        categoriesList = moneyDB.moneyDao().getCategories()
        val btnAdd : Button = view.findViewById(R.id.button2)
        editTextKwota = view.findViewById(R.id.category_edittext)
        editTextOpis = view.findViewById(R.id.category_edittext2)

    //LISTA ROZSUWANA
        val autoComplete : AutoCompleteTextView = view.findViewById(R.id.auto_complete)
        val adapter = ArrayAdapter(view.context, R.layout.list_category, categoriesList)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
        }
        dropmenu = autoComplete

    //DODAWANIE KWOT
        btnAdd.setOnClickListener {
            val value : String? = editTextKwota.text.toString()
            val description : String = editTextOpis.text.toString()
            val cat : String= dropmenu.text.toString()
            val catId : Int? = moneyDB.moneyDao().getId(cat)

            if(isValue(value)) {
                val valueDots = value!!.replace(",", ".")
                val valueDouble = BigDecimal(valueDots).setScale(2, RoundingMode.HALF_UP).toDouble()
                val newMoney = Money(
                    null, valueDouble, description, catId
                )

                GlobalScope.launch(Dispatchers.IO) {
                    moneyDB.moneyDao().insertMoney(newMoney)
                }
                Toast.makeText(context, "Dodano: $valueDouble", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Podaj właściwą kwote", Toast.LENGTH_SHORT).show()
            }
        }
*/





        return view
    }

/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
*/




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



    // MONEY INITIALIZE Z PARAMETREM GDZIE BEDZIE CATEGORY CO KLIKNIEMY I WYSWIETLA PO UPPER CATEGORY
    // UPPER CATEGORY = CATEGORY W PARAMETRZE ( DOMYSLNIE NULL )
    private fun moneyInitialize(upperId: Int?){

        moneyArrayList = arrayListOf<MoneyRV>()

        val monthList = listOf("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
            "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień")

        if(upperId == null) {
            moneyRV = moneyDB.moneyDao().getAllMoney()

            // STARE POBIERANIE KASY
        /*
            moneyRVid = moneyDB.moneyDao().getAllMoneyId()
            moneyRVname = moneyDB.moneyDao().getAllMoneyName()
            moneyRVvalue = moneyDB.moneyDao().getAllMoneyValue()
            moneyRVcat = moneyDB.moneyDao().getAllMoneyCategory()
            moneyRVdate = moneyDB.moneyDao().getAllMoneyDate()

             */
            }
        else{
            moneyRV = moneyDB.moneyDao().getAllMoneyWhereCategory(upperId)

            // STARE POBIERANIE KASY
            /*
            moneyRVid = moneyDB.moneyDao().getMoneyIdWhereCategory(upperId)
            moneyRVname = moneyDB.moneyDao().getMoneyDescriptionWhereCategory(upperId)
            moneyRVvalue = moneyDB.moneyDao().getMoneyValueWhereCategory(upperId)
            moneyRVcat = moneyDB.moneyDao().getMoneyCategoryWhereCategory(upperId)
            moneyRVdate = moneyDB.moneyDao().getMoneyDateWhereCategory(upperId)

             */
        }

        for (i in moneyRV){
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = i.moneyDate.time
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)

            val moneyData = i.id?.let {
                MoneyRV(
                    it,
                    i.moneyDescription,
                    i.moneyValue,
                    moneyDB.moneyDao().getOneMoneyCategory(i.moneyCategory_id),
                    day.toString(),
                    monthList[month],
                    year.toString()
                )
            }
            if (moneyData != null) {
                moneyArrayList.add(moneyData)
            }
        }

        // STARE DODAWANIE KASY DO RV
/*
        for (i in moneyRVname.indices)
        {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = moneyRVdate[i].time

            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val moneyData = MoneyRV(
                moneyRVid[i],
                moneyRVname[i],
                moneyRVvalue[i],
                moneyRVcat[i],
                day.toString(),
                monthList[month],
                year.toString()
            )
            moneyArrayList.add(moneyData)
        }

 */


    }


}


