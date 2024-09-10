package com.example.ouotesapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class AddQuoteActivity : AppCompatActivity() {

    private lateinit var quoteEditText: EditText
    private lateinit var authorEditText: EditText
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quote)

        // bind views
        quoteEditText = findViewById(R.id.editTextQuote)
        authorEditText = findViewById(R.id.editTextAuthor)
        addButton = findViewById(R.id.addButton)

        // listener
        addButton.setOnClickListener {
            // get text
            val quote = quoteEditText.text.toString()
            val author = authorEditText.text.toString()

            // check if empty
            if (quote.isEmpty()) {
                quoteEditText.error = "Cannot be empty"
                return@setOnClickListener
            }
            if (author.isEmpty()) {
                authorEditText.error = "Cannot be empty"
                return@setOnClickListener
            }

            // add to database
            addQuoteToDB(quote, author)
        }
    }

    private fun addQuoteToDB(quote: String, author: String) {
        // create a hashmap
        val quoteHashMap = HashMap<String, Any>()
        quoteHashMap["quote"] = quote
        quoteHashMap["author"] = author

        // instantiate database connection
        val database = FirebaseDatabase.getInstance()
        val quotesRef = database.getReference("quotes")

        val key = quotesRef.push().key
        key?.let {
            quoteHashMap["key"] = it
            quotesRef.child(it).setValue(quoteHashMap).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@AddQuoteActivity, "Added", Toast.LENGTH_SHORT).show()
                    quoteEditText.text.clear()
                    authorEditText.text.clear()
                }
            }
        }
    }
}