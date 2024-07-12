package com.example.e09showemployees.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e09showemployees.R
import com.example.e09showemployees.databinding.EmployeeItemBinding
import com.example.e09showemployees.network.model.Employee

class EmployeesAdapter(private val onItemClick: (Employee) -> Unit) : ListAdapter<Employee, EmployeesAdapter.ViewHolder>(EmployeeDiffCallback()) {

    inner class ViewHolder(val binding: EmployeeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick(getItem(adapterPosition))
            }
        }
    }

   /* override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("EmployeesAdapter","onCreateViewHolder() called")

        val binding = EmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("EmployeesAdapter", "onCreateViewHolder() called")
        Log.d("EmployeesAdapter", "Setting logging level to DEBUG")
        Log.d("EmployeesAdapter", "Current logging level: ${Log.isLoggable("EmployeesAdapter", Log.DEBUG)}")
        Log.d("EmployeesAdapter", "Changing logging level to DEBUG")
        Log.d("EmployeesAdapter", "New logging level: ${Log.isLoggable("EmployeesAdapter", Log.DEBUG)}")

        val binding = EmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    /*
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("EmployeesAdapter", "onBindViewHolder() called at position $position")
        val employee = getItem(position)
        Log.d("EmployeesAdapter", "Binding employee at position $position: $employee")

        holder.binding.nameTextView.text = employee.firstName
        holder.binding.titleTextView.text = employee.title
        holder.binding.emailTextView.text = employee.email
        holder.binding.phoneTextView.text = employee.phone
        holder.binding.departmentTextView.text = employee.department

        // Load image using Glide
        Glide.with(holder.binding.employeeImage.context)
            .load(employee.image)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.employeeImage)
    }*/

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("EmployeesAdapter", "onBindViewHolder() called at position $position")
        Log.d("EmployeesAdapter", "Setting logging level to DEBUG")
        Log.d("EmployeesAdapter", "Current logging level: ${Log.isLoggable("EmployeesAdapter", Log.DEBUG)}")
        Log.d("EmployeesAdapter", "Changing logging level to DEBUG")
        Log.d("EmployeesAdapter", "New logging level: ${Log.isLoggable("EmployeesAdapter", Log.DEBUG)}")

        val employee = getItem(position)
        Log.d("EmployeesAdapter", "Binding employee at position $position: $employee")

        holder.binding.nameTextView.text = employee.firstName
        holder.binding.titleTextView.text = employee.title
        holder.binding.emailTextView.text = employee.email
        holder.binding.phoneTextView.text = employee.phone
        holder.binding.departmentTextView.text = employee.department

        // Load image using Glide
        Glide.with(holder.binding.employeeImage.context)
            .load(employee.image)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.employeeImage)
    }

    class EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem == newItem
        }
    }
}
