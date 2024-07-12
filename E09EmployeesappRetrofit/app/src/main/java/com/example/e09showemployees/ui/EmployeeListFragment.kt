package com.example.e09showemployees.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e09showemployees.R
import com.example.e09showemployees.databinding.FragmentEmployeeListBinding
import com.example.e09showemployees.network.model.Employee
import timber.log.Timber

class EmployeeListFragment : Fragment(R.layout.fragment_employee_list) {
    private var _binding: FragmentEmployeeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EmployeeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEmployeeListBinding.bind(view)

        viewModel.employees.observe(viewLifecycleOwner) { employees ->
            setupRecyclerView(employees)
        }
    }

    internal fun setupRecyclerView(employees: List<Employee>?) {
        if (employees != null) {
            // Verify that the data being passed to the adapter is of type List<Employee>
            if (employees is List<Employee>) {
                val layoutManager = LinearLayoutManager(requireContext())
                val adapter = EmployeesAdapter { employee ->
                    val actionId = R.id.action_employeeListFragment_to_employeeDetailFragment
                    val bundle = bundleOf("employee" to employee)
                    findNavController().navigate(actionId, bundle)
                }

                binding.recyclerView.layoutManager = layoutManager

                // Set the adapter only if it is null or a new instance is needed
                if (binding.recyclerView.adapter == null || binding.recyclerView.adapter !is EmployeesAdapter) {
                    binding.recyclerView.adapter = adapter
                }

                // Add logging statements to verify the data being passed to the adapter
                Timber.d("Employees List: $employees")

                adapter.submitList(employees)
            } else {
                // Handle the case where the data being passed is not of type List<Employee>
                Timber.e("Error: Invalid data type. Expected List<Employee>, got ${employees.javaClass.simpleName}")
            }
        } else {
            // Handle the null case, such as showing an error message or an empty state
            Timber.e("Error: Employees list is null")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
