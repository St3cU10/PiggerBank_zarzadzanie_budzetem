package com.example.piggerbank

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piggerbank.Baza.Money
import com.example.piggerbank.Baza.MoneyDB
import com.example.piggerbank.RecycleView.CategoryRV
import com.example.piggerbank.RecycleView.MoneyRV
import com.example.piggerbank.RecycleView.MyAdapter
import com.example.piggerbank.RecycleView.MyAdapterCategory
import com.example.piggerbank.databinding.FragmentKategorieBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/*// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddMoneyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddMoneyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_money, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddMoneyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddMoneyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
 */

class AddMoneyFragment : Fragment() {

    private lateinit var moneyDB: MoneyDB
    private lateinit var categoriesList: List<String>
    private lateinit var textDate : TextView
    private lateinit var buttonDate : Button
    lateinit var editTextData : TextView
    lateinit var dropmenu: AutoCompleteTextView
    lateinit var editTextKwota: EditText
    lateinit var editTextOpis: EditText
    // dodac tutaj typ zmiennej od kalendarza

    private lateinit var adapter: MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var moneyArrayList: ArrayList<MoneyRV>

    lateinit var moneyRVname: Array<String>
    lateinit var moneyRVvalue: Array<Double>
    lateinit var moneyRVcat: Array<String>
    lateinit var moneyRVdate: Array<String>
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_money, container, false)


        val prevBtn : ImageButton = view.findViewById(R.id.back_button)
        prevBtn.setOnClickListener{
            val fragment = HomeFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }



        // FUNKCJE LAYOUTA
        moneyDB = MoneyDB.getInstance(MainActivity())
        categoriesList = moneyDB.moneyDao().getCategories()
        val btnAdd : Button = view.findViewById(R.id.button2)
        editTextKwota = view.findViewById(R.id.category_edittext)
        editTextOpis = view.findViewById(R.id.category_edittext2)
        textDate = view.findViewById(R.id.textDate)
        buttonDate = view.findViewById(R.id.buttonDate)
        editTextData =view.findViewById(R.id.textDate)

        //Kalendarz

       /*val datePicker = view.findViewById<DatePicker>(R.id.date_Picker)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH)){view, year, month, day ->
            val month = month +1
            val msg = "You Selected $day/$month/$year"
            Toast.makeText(this@AddMoneyFragment,msg, Toast.LENGTH_SHORT)
        }*/
        //kalendarz
        val calendarBox = Calendar.getInstance()
        val dateBox = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            calendarBox.set(Calendar.YEAR, year)
            calendarBox.set(Calendar.MONTH, month)
            calendarBox.set(Calendar.DAY_OF_MONTH, day)

            updateText(calendarBox)
        }
        //otwieranie kalendarza
        buttonDate.setOnClickListener{
           DatePickerDialog(view.context, dateBox, calendarBox.get(Calendar.YEAR), calendarBox.get(Calendar.MONTH), calendarBox.get(Calendar.DAY_OF_MONTH)).show()
        }


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
            val date: String = editTextData.text.toString()
            //zmienna z kalendarza

            if(isValue(value)) {
                val valueDots = value!!.replace(",", ".")
                val valueDouble = BigDecimal(valueDots).setScale(2, RoundingMode.HALF_UP).toDouble()
                val newMoney = Money(
                    null, valueDouble, description, catId, date
                )

                GlobalScope.launch(Dispatchers.IO) {
                    moneyDB.moneyDao().insertMoney(newMoney)
                }
                Toast.makeText(context, "Dodano: $description", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Podaj właściwą kwote", Toast.LENGTH_SHORT).show()
            }
        }

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