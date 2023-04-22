package com.example.piggerbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.piggerbank.Baza.Money
import com.example.piggerbank.Baza.MoneyDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode


class HomeFragment : Fragment() {

    private lateinit var moneyDB: MoneyDB
    private lateinit var categoriesList: List<String>
    lateinit var dropmenu: AutoCompleteTextView
    lateinit var editTextKwota: EditText
    lateinit var editTextOpis: EditText



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

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






        return view
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