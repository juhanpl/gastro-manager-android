package com.example.dishmanager

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dishmanager.adapter.DishAdapter
import com.example.dishmanager.models.Category
import com.example.dishmanager.models.Dish
import com.example.dishmanager.repository.CategoryRepository
import com.example.dishmanager.repository.DishRepository
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //init repository
        val dishRepository = DishRepository(this)
        val categoryRepository = CategoryRepository(this)

        //load dishes
        var dishes: List<Dish> = dishRepository.getDishes()

        //set components
        val txtSearch = findViewById<TextInputEditText>(R.id.txtSearch)
        val spinner = findViewById<AutoCompleteTextView>(R.id.cbCategory)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = DishAdapter(this, emptyList(), null)

        fun configSpinner() {

            val categories = categoryRepository.getCategories()

            //set adapter to spinner
            spinner.setAdapter(
                ArrayAdapter(
                    this@MainActivity,
                    R.layout.item_spinner,
                    categories
                )
            )
            //set first item as selected
            spinner.setText(categories[0], false)
            //set style to the dropdown
            spinner.setDropDownBackgroundDrawable(
                ContextCompat.getDrawable(this, R.drawable.dropdown_background)
            )

        }

        fun configRecyclerView() {

            //load the favorites dishes
            fun refreshFavoritesIfSelected() {
                val selectedCategory = spinner.text.toString()
                if (selectedCategory == "Favorite") {
                    adapter.getLikedDishes()
                }
            }
            adapter.onItemClick = ::refreshFavoritesIfSelected

            //config recyclerview
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

            //update recyclerview data
            adapter.updateData(dishes)

        }

        configSpinner()
        configRecyclerView()

        fun getDishesFromCategory(category: String) {

            val searchedCategory = when(category) {

                "All" -> dishes
                "Favorite" -> adapter.getItems()
                else -> dishes.filter { dish ->
                    dish.category == category
                }

            }

            adapter.updateData(searchedCategory)

        }

        spinner.setOnItemClickListener { parent, _, position, _ ->

            //clear search field
            txtSearch.setText("")

            val selectedCategory = spinner.adapter.getItem(position).toString()
            spinner.setText(selectedCategory, false)

            getDishesFromCategory(selectedCategory)

        }

        fun getSearchedDishes(category: String, searchedValue: String) {

            val searchedDishes = when(category) {

                "All" -> {
                    dishes.filter { dish ->
                        dish.dishName.contains(
                            searchedValue,
                            ignoreCase = true
                        )
                    }
                }
                "Favorite" -> {
                    adapter.getLikedDishes()
                    adapter.getItems().filter { dish ->
                        dish.dishName.contains(
                            searchedValue,
                            ignoreCase = true
                        )
                    }
                }
                else -> {
                    dishes.filter { dish ->
                        dish.category == category && dish.dishName.contains(
                            searchedValue,
                            ignoreCase = true
                        )
                    }
                }

            }

            adapter.updateData(searchedDishes)

        }

        txtSearch.doOnTextChanged { text, start, before, count ->

            val selectedCategory = spinner.text.toString()

            getSearchedDishes(selectedCategory, text.toString())

        }

    }

}