package com.example.piggerbank

import android.content.Context
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
import android.widget.Spinner
import android.widget.Toast
import com.example.piggerbank.Baza.Category
import com.example.piggerbank.Baza.MoneyDB
import com.example.piggerbank.databinding.ActivityMainBinding
import com.example.piggerbank.databinding.FragmentKategorieBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class KategorieFragment : Fragment() {

    private lateinit var bindingCat : FragmentKategorieBinding
    lateinit var editText: EditText
    lateinit var dropmenu: AutoCompleteTextView
    private lateinit var CatName : String
    private lateinit var CatUpper : String
    private lateinit var moneyDB: MoneyDB
    private lateinit var categoriesList:  List<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TUTAJ FUNKCJE LAYOUTA
        moneyDB = MoneyDB.getInstance(MainActivity())
        categoriesList = moneyDB.moneyDao().getCategories()
        val view = inflater.inflate(R.layout.fragment_kategorie, container, false)
        val btn : Button = view.findViewById(R.id.button)
        editText = view.findViewById(R.id.category_edittext)


        //LISTA ROZSUWANA
        val autoComplete : AutoCompleteTextView = view.findViewById(R.id.auto_complete)
        val adapter = ArrayAdapter(view.context, R.layout.list_category, categoriesList)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Toast.makeText(context, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
        }
        dropmenu = autoComplete


        //DODAWANIE KATEGORII
        btn.setOnClickListener {
            CatName = editText.text.toString()
            CatUpper = dropmenu.text.toString()
            val compareCat: String? = moneyDB.moneyDao().getCat(CatName)
            if(CatName != null && compareCat == null){
            val catUpperId = moneyDB.moneyDao().getId(CatUpper)

            val newCat = Category(
                null, CatName, catUpperId
            )
            GlobalScope.launch(Dispatchers.IO) {
                moneyDB.moneyDao().insertCategory(newCat)
            }

                categoriesList = moneyDB.moneyDao().getCategories()
        }
            else{
                Toast.makeText(context, "Zle dane", Toast.LENGTH_SHORT).show()
            }
        }




        // Inflate the layout for this fragment
        return view
    }



}