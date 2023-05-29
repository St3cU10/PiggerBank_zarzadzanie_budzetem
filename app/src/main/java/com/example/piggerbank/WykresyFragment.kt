package com.example.piggerbank

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.db.williamchart.view.BarChartView


class WykresyFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wykresy, container, false)
        val wykresprzychody : BarChartView = view.findViewById(R.id.barChart)
        val wykreswydatki : BarChartView = view.findViewById(R.id.barChart2)


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



    companion object {

        private val SetPrzychody = listOf(
            "JAN" to 4F,
            "FEB" to 1F,
            "MAR" to 2F,
            "MAY" to 2.3F,
            "APR" to 5F,
            "JUN" to 4F
        )

        private val SetWydatki = listOf(
            "JAN" to 4F,
            "FEB" to 1F,
            "MAR" to 2F,
            "MAY" to 2.3F,
            "APR" to 5F,
            "JUN" to 4F
        )

        private const val animationDuration = 1000L
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

