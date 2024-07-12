package com.example.e09showemployees.ui

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.example.e09showemployees.R
import com.example.e09showemployees.network.EmployeeApiClient
import com.example.e09showemployees.network.EmployeeService
import com.example.e09showemployees.databinding.FragmentEmployeeDetailsBinding
import com.example.e09showemployees.ui.EmployeeViewModel
import com.example.e09showemployees.ui.EmployeeViewModelFactory
import com.example.e09showemployees.network.model.Employee


class EmployeeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeDetailsBinding
    private val employeeService: EmployeeService = EmployeeApiClient.getInstance().apiService
    private val viewModel: EmployeeViewModel by viewModels { MyEmployeeViewModelFactory(employeeService) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEmployeeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val employeeId = requireArguments().getInt("employeeId")
        viewModel.getEmployeeById(employeeId).observe(viewLifecycleOwner, { employee ->
            if (employee != null) {
                // Set data to TextViews
                binding.nameTextView.text = employee.firstName
                binding.titleTextView.text = employee.title
                binding.emailTextView.text = employee.email
                binding.phoneTextView.text = employee.phone
                binding.departmentTextView.text = employee.department

                // Load image using Glide
                Glide.with(binding.employeeImage.context)
                    .load(employee.image)
                    .placeholder(R.drawable.color_placeholder)
                    .into(binding.employeeImage)

            }
        })
    }
}
