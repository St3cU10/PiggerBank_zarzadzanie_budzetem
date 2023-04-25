package com.example.piggerbank

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.piggerbank.Baza.Category
import com.example.piggerbank.Baza.MoneyDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class edit_category(val catID : Int) : Fragment() {

    private lateinit var moneyDB: MoneyDB
    private lateinit var categoriesList : List<String>

    private lateinit var oldName : TextView

    private lateinit var newName : EditText


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_category, container, false)
        moneyDB = MoneyDB.getInstance(MainActivity())


        val prevBtn : ImageButton = view.findViewById(R.id.back_button)
        prevBtn.setOnClickListener{
            val fragment = KategorieFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }

        val btnAdd : Button = view.findViewById(R.id.button2)

        oldName = view.findViewById(R.id.oldName)

        oldName.text = moneyDB.moneyDao().getOneCatName(catID)

        newName = view.findViewById(R.id.newName)
        newName.setText(oldName.text)

        btnAdd.setOnClickListener {
            val name : String = newName.text.toString()

            GlobalScope.launch (Dispatchers.IO){
                moneyDB.moneyDao().updateCategory(name, catID)
            }

            val fragment = KategorieFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }



        return view
    }


}