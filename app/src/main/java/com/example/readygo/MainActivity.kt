package com.example.readygo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import android.util.Log

class MainActivity : AppCompatActivity() {

    // Declare parallel arrays to store packing list data
    companion object {
        val items = ArrayList<String>()
        val categories = ArrayList<String>()
        val quantities = ArrayList<Int>()
        val comments = ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons from the layout
        val btnAddToList = findViewById<Button>(R.id.btnAddToList)
        val btnGoToSecondScreen = findViewById<Button>(R.id.btnGoToSecondScreen)
        val btnExit = findViewById<Button>(R.id.btnExit)

        // Log to show the main activity has been created
        Log.i("MainActivity", "MainActivity created successfully.")

        // Pre-populate with some example data
        if (items.isEmpty()) {
            items.add("T-Shirts and pants")
            categories.add("Clothing")
            quantities.add(5)
            comments.add("Comfortable for travel")

            items.add("Toothbrush")
            categories.add("Toiletries")
            quantities.add(1)
            comments.add("Essential for hygiene")
        }


        // Set click listener for the "Add to Packing List" button
        btnAddToList.setOnClickListener {
            Log.d("MainActivity", "Add to Packing List button clicked.")
            showAddItemDialog()
        }

        // Set click listener to navigate to the second screen
        btnGoToSecondScreen.setOnClickListener {
            Log.d("MainActivity", "Navigate to SecondActivity button clicked.")
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // Set click listener for the "Exit" button
        btnExit.setOnClickListener {
            Log.d("MainActivity", "Exit button clicked.")
            finishAffinity() // Closes the app
        }
    }

    // Function to show a dialog for adding a new packing item
    private fun showAddItemDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Packing Item")

        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_add_item, null)
        val etItemName = dialogLayout.findViewById<EditText>(R.id.etItemName)
        val etCategory = dialogLayout.findViewById<EditText>(R.id.etCategory)
        val etQuantity = dialogLayout.findViewById<EditText>(R.id.etQuantity)
        val etComments = dialogLayout.findViewById<EditText>(R.id.etComments)

        builder.setView(dialogLayout)

        // Set the "Add" button and its click listener
        builder.setPositiveButton("Add") { dialog, which ->
            val itemName = etItemName.text.toString()
            val category = etCategory.text.toString()
            val quantityStr = etQuantity.text.toString()
            val commentsText = etComments.text.toString()

            // Error handling for empty fields and invalid quantity
            if (itemName.isNotBlank() && category.isNotBlank() && quantityStr.isNotBlank()) {
                try {
                    val quantity = quantityStr.toInt()
                    if (quantity > 0) {
                        items.add(itemName)
                        categories.add(category)
                        quantities.add(quantity)
                        comments.add(commentsText)
                        Toast.makeText(this, "Item Added!", Toast.LENGTH_SHORT).show()
                        Log.i("MainActivity", "New item '$itemName' added to the list.")
                    } else {
                        Toast.makeText(this, "Quantity must be a positive number.", Toast.LENGTH_LONG).show()
                        Log.e("MainActivity", "Invalid quantity entered: $quantityStr")
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Please enter a valid number for quantity.", Toast.LENGTH_LONG).show()
                    Log.e("MainActivity", "NumberFormatException for quantity input: $quantityStr")
                }
            } else {
                Toast.makeText(this, "Please fill all required fields.", Toast.LENGTH_LONG).show()
                Log.w("MainActivity", "Attempted to add an item with empty fields.")
            }
        }

        // Set the "Cancel" button
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }
}