package com.example.piggerbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.db.williamchart.view.BarChartView
import com.example.piggerbank.Baza.MoneyDB

class WykresyFragment : Fragment() {


    private lateinit var moneyDB: MoneyDB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wykresy, container, false)

        //przycisk cofania

        val prevBtn : ImageButton = view.findViewById(R.id.back_button)
        prevBtn.setOnClickListener{
            val fragment = HomeFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }
        moneyDB = MoneyDB.getInstance(MainActivity())

        val wykresprzychody : BarChartView = view.findViewById(R.id.barChart2)
        val wykreswydatki : BarChartView = view.findViewById(R.id.barChart)
        val przychodyText : TextView = view.findViewById(R.id.tvChartName2)
        val wydatkiText : TextView = view.findViewById(R.id.tvChartName)


        val SetPrzychody = ArrayList<Pair<String, Float>>()


        val SetWydatki = ArrayList<Pair<String, Float>>()

        val przychodyList = arrayListOf(1)
        val wydatkiList = arrayListOf(2)
        categoryIdList(1, 0, przychodyList)
        categoryIdList(2, 0, wydatkiList)

        if(przychodyList.size == 1 || moneyDB.moneyDao().getSumValueWhereCat(1) == 0F){
            wykresprzychody.visibility = View.GONE
            przychodyText.text = "Brak przychodów do porównania"
        }
        if (wydatkiList.size == 1 || moneyDB.moneyDao().getSumValueWhereCat(2) == 0F){
            wykreswydatki.visibility = View.GONE
            wydatkiText.text = "Brak wydatków do porównania"
        }

        for(i in przychodyList){
            val catList = arrayListOf(i)
            categoryIdList(i, 0, catList)

            var suma = 0.0
            for (j in catList){
                suma += moneyDB.moneyDao().getSumValueWhereCat(j)
            }
            if(suma == 0.0)
                continue
            val catName = moneyDB.moneyDao().getOneCatName(i)
            SetPrzychody.add(catName to suma.toFloat())
        }

        for(i in wydatkiList){
            val catList = arrayListOf(i)
            categoryIdList(i, 0, catList)

            var suma = 0.0
            for (j in catList){
                suma += moneyDB.moneyDao().getSumValueWhereCat(j)
            }

            if(suma == 0.0)
                continue
            val catName = moneyDB.moneyDao().getOneCatName(i)
            SetWydatki.add(catName to suma.toFloat())
        }


        wykresprzychody.animate(SetPrzychody)

        wykreswydatki.animate(SetWydatki)

        /*val prevBtn : ImageButton = view.findViewById(R.id.back_button2)
        prevBtn.setOnClickListener{
            val fragment = KategorieFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }*/
        return view
    }



    fun categoryIdList(upperId: Int, offset: Int, catList: java.util.ArrayList<Int>) {
        val cat = moneyDB.moneyDao().getCategoryDownList(upperId, offset)
        if (cat == null)
            return

        catList.add(moneyDB.moneyDao().getCategoryIdDownList(upperId, offset))
        val id = moneyDB.moneyDao().getId(cat)
        if (id != null) {
            categoryIdList(id, 0, catList)
        }
        categoryIdList(upperId, offset = offset+1, catList)

    }
}








/*class BarChartActivity : AppCompatActivity() {

    private var _binding: ActivityBarChartBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBarChartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            barChart.animation.duration = animationDuration
            barChart.animate(barSet)

            barChartHorizontal.animation.duration = animationDuration
            barChartHorizontal.animate(horizontalBarSet)

        }
    }*/

