package com.example.dishmanager

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishmanager.models.Dish
import com.example.dishmanager.repository.AssetRepository
import com.example.dishmanager.repository.DishRepository
import org.json.JSONObject

class DishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dish)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val json = intent.getStringExtra("json") ?: "{}"
        val dish = DishRepository(this).JsonToDish(json)

        val txtDishName = findViewById<TextView>(R.id.txtDishName)
        val txtDishDescription = findViewById<TextView>(R.id.txtDescription)
        val txtIngredients = findViewById<TextView>(R.id.txtIngredients)
        val txtCookingTime = findViewById<TextView>(R.id.txtCookingTime)
        val imageDish = findViewById<ImageView>(R.id.imageDish)

        fun bind() {

            txtDishName.text = dish.dishName
            txtDishName.isSelected = true
            txtDishDescription.text = dish.description
            txtIngredients.text = dish.ingredients
            txtCookingTime.text = dish.cookingTime
            imageDish.setImageBitmap(AssetRepository(this).getImageFromAssets(dish.imagePath))

        }

        bind()
    }

}