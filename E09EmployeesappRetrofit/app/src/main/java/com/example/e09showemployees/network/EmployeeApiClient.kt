package com.example.e09showemployees.network
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmployeeApiClient {

    private val BASE_URL = "employees.json"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService: EmployeeService = retrofit.create(EmployeeService::class.java)

    companion object {
        @Volatile
        private var INSTANCE: EmployeeApiClient? = null

        fun getInstance(): EmployeeApiClient {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: EmployeeApiClient().also { INSTANCE = it }
            }
        }
    }
}
