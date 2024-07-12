package com.example.e08showemployees

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.e08showemployees.databinding.ActivityEmployeeBinding




class EmployeeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val employeeName = intent.getStringExtra(EXTRA_EMPLOYEE_NAME)
        val employeeEmail = intent.getStringExtra(EXTRA_EMPLOYEE_EMAIL)

        binding.tvEmployeeName.text = employeeName
        binding.tvEmployeeEmail.text = employeeEmail
    }

    companion object {
        const val EXTRA_EMPLOYEE_NAME = "employeeName"
        const val EXTRA_EMPLOYEE_EMAIL = "employeeEmail"
    }
}
