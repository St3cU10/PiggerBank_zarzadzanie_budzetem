package com.example.piggerbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.piggerbank.Baza.Category
import com.example.piggerbank.Baza.MoneyDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddCategoryFragment : Fragment() {

    private lateinit var moneyDB: MoneyDB
    private lateinit var categoriesList: List<String>
    lateinit var dropmenu: AutoCompleteTextView
    lateinit var editTextCatName: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_category, container, false)

        moneyDB = MoneyDB.getInstance(MainActivity())
        categoriesList = moneyDB.moneyDao().getCategories()

        editTextCatName = view.findViewById(R.id.category_edittext)

        // LISTA ROZSUWANA
        val autoComplete : AutoCompleteTextView = view.findViewById(R.id.auto_complete)
        val adapter = ArrayAdapter(view.context, R.layout.list_category, categoriesList)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
        }
        dropmenu = autoComplete

        val btnAdd : Button = view.findViewById(R.id.button)

        btnAdd.setOnClickListener {
            val CatName = editTextCatName.text.toString()
            val CatUpper = dropmenu.text.toString()
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

                val fragment = KategorieFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container,fragment)?.commit()
            }
            else{
                Toast.makeText(context, "Zle dane", Toast.LENGTH_SHORT).show()
            }
        }


            // BACK BUTTON
        val prevBtn : ImageButton = view.findViewById(R.id.back_button)
        prevBtn.setOnClickListener{
            val fragment = KategorieFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }


        return view
    }


}