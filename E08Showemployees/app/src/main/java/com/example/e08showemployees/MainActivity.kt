package com.example.e08showemployees

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.json.JSONArray
import com.example.e08showemployees.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadJSONData()
    }

    private fun loadJSONData() {
        val assetManager = assets
        val inputStream = assetManager.open("employees.json")
        val jsonText = inputStream.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonText)
        val employees = jsonObject.getJSONArray("employees")
        setupRecyclerView(employees)
    }

    private fun setupRecyclerView(employees: JSONArray) {
        val layoutManager = LinearLayoutManager(this)
        val adapter = EmployeesAdapter(employees) { position ->
            // Handle the click event here
            val employee = employees.getJSONObject(position)

            // Start EmployeeDetailActivity with employee data
            val intent = Intent(this, EmployeeDetailActivity::class.java)
            intent.putExtra("employee", employee.toString())
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

}
