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
            val value : Float? = editTextKwota.text.toString().toFloat()
            val description : String = editTextOpis.text.toString()
            val cat : String= dropmenu.text.toString()
            val catId : Int? = moneyDB.moneyDao().getId(cat)

            if(value != null) {
                val newMoney = Money(
                    null, value, description, catId
                )

                GlobalScope.launch(Dispatchers.IO) {
                    moneyDB.moneyDao().insertMoney(newMoney)
                }
            }
            else{
                Toast.makeText(context, "Podaj kwote", Toast.LENGTH_SHORT).show()
            }
        }






        return view
    }


}