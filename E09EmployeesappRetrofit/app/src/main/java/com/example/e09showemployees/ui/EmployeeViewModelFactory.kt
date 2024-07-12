package com.example.e09showemployees.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e09showemployees.network.EmployeeService
import com.example.e09showemployees.ui.EmployeeViewModel

abstract class EmployeeViewModelFactory(private val employeeService: EmployeeService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            return EmployeeViewModel(employeeService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MyEmployeeViewModelFactory(private val employeeService: EmployeeService) : EmployeeViewModelFactory(employeeService) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            return EmployeeViewModel(employeeService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
