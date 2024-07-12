package com.example.e08showemployees

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.e08showemployees.databinding.ActivityEmployeeDetailBinding
import org.json.JSONObject
import com.bumptech.glide.Glide

class EmployeeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmployeeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val employeeJson = intent.getStringExtra("employee")
        val employee = JSONObject(employeeJson)

        // Set data to TextViews
        binding.nameTextView.text = employee.getString("firstName")
        binding.titleTextView.text = employee.getString("title")
        binding.emailTextView.text = employee.getString("email")
        binding.phoneTextView.text = employee.getString("phone")
        binding.departmentTextView.text = employee.getString("department")

        // Load image using Glide
        Glide.with(binding.employeeImage.context)
            .load(employee.getString("image"))
            .placeholder(R.drawable.ic_launcher_background)
            .into(binding.employeeImage)
    }
}
