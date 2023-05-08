package com.example.piggerbank

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.piggerbank.Baza.Money
import com.example.piggerbank.Baza.MoneyDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*


class EditMoneyFragment(val moneyID : Int) : Fragment() {

    private lateinit var moneyDB: MoneyDB
    private lateinit var categoriesList : List<String>

    private lateinit var oldName : TextView
    private lateinit var oldCategory : TextView
    private lateinit var oldValue : TextView
    private lateinit var oldDate : TextView

    private lateinit var newNameEditText : EditText
    private lateinit var newCatDropMenu: AutoCompleteTextView
    private lateinit var newValueEditText: EditText
    private lateinit var newDataTextView: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_money, container, false)
        moneyDB = MoneyDB.getInstance(MainActivity())

        val prevBtn : ImageButton = view.findViewById(R.id.back_button)
        prevBtn.setOnClickListener{
            val fragment = HomeFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }

        // USUWANIE
        val DeleteButoon : ImageButton = view.findViewById(R.id.delete_button)
        DeleteButoon.setOnClickListener{
            moneyDB.moneyDao().deleteOneMoney(moneyID)
            val fragment = HomeFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()

        }

        categoriesList = moneyDB.moneyDao().getCategories()
        val btnAdd : Button = view.findViewById(R.id.button2)
        val buttonDate : Button = view.findViewById(R.id.buttonDate)


        //oldName = view.findViewById(R.id.oldName)
        //oldCategory = view.findViewById(R.id.oldCat)
       // oldValue = view.findViewById(R.id.oldValue)
        //oldDate = view.findViewById(R.id.oldDate)

       //oldName = moneyDB.moneyDao().getOneMoneyDescription(moneyID)
       //oldCategory = moneyDB.moneyDao().getOneMoneyCategory(moneyID)
        //oldValue = moneyDB.moneyDao().getOneMoneyValue(moneyID).toString()
        //oldDate = moneyDB.moneyDao().getOneMoneyDate(moneyID)

        newNameEditText = view.findViewById(R.id.newName)
        newValueEditText = view.findViewById(R.id.newValue)
        newDataTextView = view.findViewById(R.id.newDate)
        //newDataTextView.text = oldDate.text.toString()

        newNameEditText.setText(moneyDB.moneyDao().getOneMoneyDescription(moneyID))
        newValueEditText.setText(moneyDB.moneyDao().getOneMoneyValue(moneyID).toString())


        // LISTA ROZSUWANA
        val autoComplete : AutoCompleteTextView = view.findViewById(R.id.newCat)
        val adapter = ArrayAdapter(view.context, R.layout.list_category, categoriesList)
        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
        }
        newCatDropMenu = autoComplete

        newCatDropMenu.setText(moneyDB.moneyDao().getOneMoneyCategory(moneyID))
        // KALENDARZ
        val calendarBox = Calendar.getInstance()
        val dateBox = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            calendarBox.set(Calendar.YEAR, year)
            calendarBox.set(Calendar.MONTH, month)
            calendarBox.set(Calendar.DAY_OF_MONTH, day)

            updateText(calendarBox)
        }
        //otwieranie kalendarza
        buttonDate.setOnClickListener{
            DatePickerDialog(view.context, dateBox, calendarBox.get(Calendar.YEAR), calendarBox.get(
                Calendar.MONTH), calendarBox.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnAdd.setOnClickListener {
            val name : String? = newNameEditText.text.toString()
            val value : String? = newValueEditText.text.toString()
            val cat : String? = newCatDropMenu.text.toString()
            val catId : Int? = moneyDB.moneyDao().getId(cat)
            val date : String = newDataTextView.text.toString()

            if(isValue(value) && name != null && catId != null && date != "Wybierz datę:") {
                val valueDots = value!!.replace(",", ".")
                val valueDouble = BigDecimal(valueDots).setScale(2, RoundingMode.HALF_UP).toDouble()


                GlobalScope.launch(Dispatchers.IO) {
                    moneyDB.moneyDao().updateMoney(name, valueDouble, catId, date, moneyID)
                }
                Toast.makeText(context, "Zedytowano: $name", Toast.LENGTH_SHORT).show()
                val fragment = HomeFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container,fragment)?.commit()
            }
            else{
                Toast.makeText(context, "Podaj właściwe dane", Toast.LENGTH_SHORT).show()
            }
        }



        // Inflate the layout for this fragment
        return view
    }


    private fun updateText(calendar: Calendar){
        val dateFormat = "dd-MM-yyyy"
        val simple = SimpleDateFormat(dateFormat, Locale.UK)
        newDataTextView.setText(simple.format(calendar.time))

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