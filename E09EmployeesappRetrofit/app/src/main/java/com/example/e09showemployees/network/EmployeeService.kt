package com.example.e09showemployees.network

import com.example.e09showemployees.network.model.EmployeesResponse
import com.example.e09showemployees.network.model.Employee
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeService {
    @GET("android_employees.json")
    suspend fun getEmployees(): Response<EmployeesResponse>

    @GET("employees/{id}")
    suspend fun getEmployeeById(@Path("id") employeeId: Int): Response<Employee>

    companion object {
        operator fun invoke(): EmployeeService {
            return Retrofit.Builder()
                .baseUrl("https://ptm.fi/data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(EmployeeService::class.java)
        }
    }
}
