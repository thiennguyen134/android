package com.example.e09showemployees.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.e09showemployees.R
import com.example.e09showemployees.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the NavController from the NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.employeeListFragment, R.id.employeeDetailFragment
        ).build()

        // Set up ActionBar
        // setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // Observe data
        viewModel.employees.observe(this, { employees ->
            // Pass the data to the fragments
            // Here, we get the employee list fragment and pass the data to it
            val employeeListFragment = supportFragmentManager.findFragmentById(R.id.employeeListFragment)
            if (employeeListFragment is EmployeeListFragment) {
                employeeListFragment.setupRecyclerView(employees)
            }
        })

        // Fetch employees data
        viewModel.fetchEmployees()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null) || super.onSupportNavigateUp()
    }
}
