package com.example.piggerbank

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piggerbank.Baza.Category
import com.example.piggerbank.Baza.MoneyDB
import com.example.piggerbank.RecycleView.CategoryRV
import com.example.piggerbank.RecycleView.MyAdapterCategory
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


    private lateinit var adapter : MyAdapterCategory
    private lateinit var recyclerView : RecyclerView
    private lateinit var categoryArrayList : ArrayList<CategoryRV>

    lateinit var categoryRVname : Array<String>
    lateinit var categoryRVupper : Array<String>
    lateinit var categoryRVid : Array<Int>

    lateinit var categoryRV : List<Category>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kategorie, container, false)

        val nextBtn : Button = view.findViewById(R.id.addCategory)
        nextBtn.setOnClickListener{
            val fragment = AddCategoryFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }


        val prevBtn : ImageButton = view.findViewById(R.id.back_button)
        prevBtn.setOnClickListener{
            val fragment = HomeFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container,fragment)?.commit()
        }

        moneyDB = MoneyDB.getInstance(MainActivity())

        // RECYCLER VIEW
        categoryInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view_category)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapterCategory(categoryArrayList)
        recyclerView.adapter = adapter


        //PRZECHODZENIE DO EDYCJI
        adapter.setOnItemClickListener(object : MyAdapterCategory.onItemClickListener{
            override fun onItemClick(position: Int) {
                val id = categoryArrayList[position].id
                val fragment = edit_category(id)
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container,fragment)?.commit()
            }
        })



        // STARE DZIALA
    /*
        //TUTAJ FUNKCJE LAYOUTA
        moneyDB = MoneyDB.getInstance(MainActivity())
        categoriesList = moneyDB.moneyDao().getCategories()
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



*/
        // Inflate the layout for this fragment
        return view
    }

    private fun categoryInitialize(){

        categoryArrayList = arrayListOf<CategoryRV>()
        categoryRV = moneyDB.moneyDao().getAllCategory()

        for (i in categoryRV)
        {
            val categoryData = i.id?.let {
                CategoryRV(
                    it,
                    i.categoryName,
                    moneyDB.moneyDao().getupperCatName(i.upperCategory)
                )
            }
            if (categoryData != null) {
                categoryArrayList.add(categoryData)
            }
        }
/*

        categoryRVid = moneyDB.moneyDao().getAllCategoryId()
        categoryRVname = moneyDB.moneyDao().getAllCategoryName()
        categoryRVupper = moneyDB.moneyDao().getAllUpperCategory()



        for (i in categoryRVname.indices)
        {
            val categoryData = CategoryRV(
                categoryRVid[i],
                categoryRVname[i],
                categoryRVupper[i]
            )
            categoryArrayList.add(categoryData)
        }

 */
    }

}