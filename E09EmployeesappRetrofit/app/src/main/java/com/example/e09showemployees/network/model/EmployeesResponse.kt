package com.example.e09showemployees.network.model

import com.google.gson.annotations.SerializedName

data class EmployeesResponse(
    @SerializedName("employees")
    val employees: List<Employee>
)
