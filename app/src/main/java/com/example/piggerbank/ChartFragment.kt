package com.example.piggerbank


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton




class ChartFragment : Fragment() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chart, container, false)

       /* val prevBtn : ImageButton = view.findViewById(R.id.back_button)
        prevBtn.setOnClickListener{
            val fragment = KategorieFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }*/
        return view
    }


}