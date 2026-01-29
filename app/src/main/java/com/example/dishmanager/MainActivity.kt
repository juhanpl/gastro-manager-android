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
        setContentView(R.layout.activity_main)

        var dishes: List<Dish> = emptyList()
        val dishRepository = DishRepository(this)
        val categoryRepository = CategoryRepository(this)

        val txtSearch = findViewById<TextInputEditText>(R.id.txtSearch)
        val spinner = findViewById<AutoCompleteTextView>(R.id.cbCategory)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = DishAdapter(this, emptyList(), null)

        fun verifyFavorites() {

            val selectedCategory = spinner.text.toString()
            if (selectedCategory == "Favorite") {
                adapter.getLikedDishes()
            }

        }

        adapter.onItemClick = ::verifyFavorites

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        spinner.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(this, R.drawable.dropdown_background)
        )

        lifecycleScope.launch {

            val categories = categoryRepository.getCategories()
            val spinnerAdapter = ArrayAdapter(
                this@MainActivity,
                R.layout.item_spinner,
                categories
                )
            spinner.setAdapter(spinnerAdapter)
            spinner.setText("All", false)

            dishes = dishRepository.getDishes()
            adapter.updateData(dishes)

        }

        spinner.setOnItemClickListener { parent, _, position, _ ->

            val selectedCategory = spinner.adapter.getItem(position).toString()
            spinner.setText(selectedCategory, false)

            if (selectedCategory == "All") {

                adapter.updateData(dishes)

            } else if (selectedCategory == "Favorite") {

                adapter.getLikedDishes()

            } else {

                val searchedCategory = dishes.filter { dish -> dish.category == selectedCategory}
                adapter.updateData(searchedCategory)

            }

        }

        txtSearch.doOnTextChanged { text, start, before, count ->

            val selectedCategory = spinner.text.toString()

            if (selectedCategory == "All") {

                val searchedDishes = dishes.filter { dish -> dish.dishName.contains(text.toString(), ignoreCase = true)}
                adapter.updateData(searchedDishes)


            } else if (selectedCategory == "Favorite") {

                adapter.getLikedDishes()
                val favorites = adapter.getItems()
                val searchedDishes = favorites.filter { dish -> dish.dishName.contains(text.toString(), ignoreCase = true)}
                adapter.updateData(searchedDishes)


            } else {

                val searchedCategory = dishes.filter { dish -> dish.category == selectedCategory && dish.dishName.contains(text.toString(), ignoreCase = true)}
                adapter.updateData(searchedCategory)

            }

        }

    }

}