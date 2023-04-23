package com.example.piggerbank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.piggerbank.Baza.Category
import com.example.piggerbank.Baza.Money
import com.example.piggerbank.Baza.MoneyDB
import com.example.piggerbank.databinding.FragmentKategorieBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var categoriesList: List<String>

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var moneyDB: MoneyDB
    //private lateinit var bindingCat: FragmentKategorieBinding
    //private lateinit var CatName: String
    //private lateinit var CatUpper: String

    //lateinit var categoryName : EditText
    //lateinit var upperCategory : String

    private val defaultCategories = listOf(
        Category(2, "WYDATKI", null),
        Category(1, "PRZYCHODY", null),

        // KATEGORIE TESTOWE
        Category(3, "AAA", 1),
        Category(4, "BBB", 2),
        Category(5, "CCC", 3)
    )

        // KASA TESTOWA
    private val testMoney = listOf(
        Money(1, 1000.00, "Wypłata", 1, "10-04-2023"),
        Money(2, 21.37, "Kremówki", 2, "21-03-2023"),
        Money(3, 4.20, "Koszenie trawy", 1, "23-04-2023")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // bindingCat = FragmentKategorieBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

/*
        bindingCat.button.setOnClickListener {
            CatName = bindingCat.categoryEdittext.text.toString()
            CatUpper = bindingCat.categoriesSpinner.toString()

            if(CatName == null)
                Toast.makeText(this, "Brak danych", Toast.LENGTH_SHORT).show()
            else{
                val CatUpperId : Int? = moneyDB.moneyDao().getId(CatUpper)
                val newCat = Category(
                    null, CatName, CatUpperId
                )

                GlobalScope.launch(Dispatchers.IO){
                    moneyDB.moneyDao().insertCategory(newCat)
                }
            }
        }
*/
        //BAZA

        moneyDB = MoneyDB.getInstance(this)

        defaultCategories.forEach {
            moneyDB.moneyDao().insertCategory(it)
        }

        testMoney.forEach {
            moneyDB.moneyDao().insertMoney(it)
        }

        categoriesList = moneyDB.moneyDao().getCategories()

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView =findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
    }

    fun getMoneyDb(): MoneyDB{
        return moneyDB
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()

            R.id.nav_kategorie -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, KategorieFragment()).commit()

            R.id.nav_skarbonka -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SkarbonkaFragment()).commit()

            R.id.nav_cele -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CeleFragment()).commit()

            R.id.nav_alerty -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PrzypomnieniaFragment()).commit()

            R.id.nav_motywacja -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MotywacjaFragment()).commit()

            R.id.nav_wykresy -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WykresyFragment()).commit()

            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment()).commit()

            R.id.nav_about -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AboutFragment()).commit()

            R.id.nav_logout -> Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else{
            onBackPressedDispatcher.onBackPressed()
        }
    }

}