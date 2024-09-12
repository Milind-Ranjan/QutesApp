package com.example.ouotesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var quoteAdapter: QuoteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private var quoteList = mutableListOf<Quote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize FloatingActionButton
        floatingActionButton = findViewById(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddQuoteActivity::class.java)
            startActivity(intent)
        }

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up Firebase database reference
        database = FirebaseDatabase.getInstance().getReference("quotes")

        // Initialize adapter
        quoteAdapter = QuoteAdapter(quoteList)
        recyclerView.adapter = quoteAdapter

        // Fetch data from Firebase
        fetchQuotesFromFirebase()
    }

    private fun fetchQuotesFromFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                quoteList.clear()
                for (quoteSnapshot in snapshot.children) {
                    val quote = quoteSnapshot.getValue(Quote::class.java)
                    quote?.let {
                        quoteList.add(it)
                    }
                }
                quoteAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}