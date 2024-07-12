package com.example.e09showemployees.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e09showemployees.network.EmployeeService
import com.example.e09showemployees.network.model.Employee
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class EmployeeViewModel(private val employeeService: EmployeeService = EmployeeService()) : ViewModel() {
    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>>
        get() = _employees

    init {
        fetchEmployees()
    }

    internal fun fetchEmployees() {
        viewModelScope.launch {
            try {
                val response = employeeService.getEmployees()
                if (response.isSuccessful) {
                    val employeesList = response.body()
                    if (employeesList != null) {
                        _employees.value = employeesList.employees
                    } else {
                        Timber.e("Error fetching employees: Response body is null")
                    }
                    Timber.d("Fetched employees: $employeesList")
                } else {
                    Timber.e("Error fetching employees: ${response.message()}")
                }
            } catch (e: Exception) {
                Timber.e(e, "Error fetching employees")
            }
        }
    }

    internal fun updateEmployeesList() {
        fetchEmployees()
    }

    fun getEmployeeById(id: Int): LiveData<Employee?> {
        val employeeLiveData = MutableLiveData<Employee?>()
        viewModelScope.launch {
            try {
                val response = employeeService.getEmployeeById(id)
                if (response.isSuccessful) {
                    val employee = response.body()
                    employeeLiveData.value = employee
                    Timber.d("Employee with id $id: $employee")
                } else {
                    throw HttpException(response)
                }
            } catch (exception: HttpException) {
                val error = exception.response()?.errorBody()?.string() ?: exception.message()
                Timber.e("Error getting employee with id $id: $error")
            } catch (e: Exception) {
                Timber.e(e, "Error getting employee with id $id")
            }
        }
        return employeeLiveData
    }
}
