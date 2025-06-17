package com.example.readygo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // Initialize UI elements from the layout
        val btnDisplayList = findViewById<Button>(R.id.btnDisplayList)
        val btnDisplayFiltered = findViewById<Button>(R.id.btnDisplayFiltered)
        val btnReturn = findViewById<Button>(R.id.btnReturn)
        val tvPackingList = findViewById<TextView>(R.id.tvPackingList)

        Log.i("SecondActivity", "SecondActivity created successfully.")


        // Set click listener to display the full packing list
        btnDisplayList.setOnClickListener {
            Log.d("SecondActivity", "Display Packing List button clicked.")
            if (MainActivity.items.isEmpty()) {
                tvPackingList.text = "Your packing list is empty."
                Toast.makeText(this, "Nothing to show.", Toast.LENGTH_SHORT).show()
            } else {
                val listBuilder = StringBuilder()
                for (i in MainActivity.items.indices) {
                    listBuilder.append("Item: ${MainActivity.items[i]}\n")
                    listBuilder.append("Category: ${MainActivity.categories[i]}\n")
                    listBuilder.append("Quantity: ${MainActivity.quantities[i]}\n")
                    listBuilder.append("Comments: ${MainActivity.comments[i]}\n\n")
                }
                tvPackingList.text = listBuilder.toString()
            }
        }

        // Set click listener to display items with quantity >= 2
        btnDisplayFiltered.setOnClickListener {
            Log.d("SecondActivity", "Display Filtered List button clicked.")
            val filteredListBuilder = StringBuilder()
            var itemsFound = false
            for (i in MainActivity.items.indices) {
                if (MainActivity.quantities[i] >= 2) {
                    itemsFound = true
                    filteredListBuilder.append("Item: ${MainActivity.items[i]}\n")
                    filteredListBuilder.append("Quantity: ${MainActivity.quantities[i]}\n\n")
                }
            }

            if (itemsFound) {
                tvPackingList.text = filteredListBuilder.toString()
            } else {
                tvPackingList.text = "No items with quantity of 2 or more."
                Toast.makeText(this, "No items match the filter.", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener to return to the main screen
        btnReturn.setOnClickListener {
            Log.d("SecondActivity", "Return to Main Screen button clicked.")
            finish() // Closes the current activity
        }
    }
}