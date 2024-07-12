package com.example.e08showemployees

import com.bumptech.glide.Glide
import com.example.e08showemployees.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e08showemployees.databinding.EmployeeItemBinding
import org.json.JSONArray

class EmployeesAdapter(private val employees: JSONArray, private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: EmployeeItemBinding, onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = employees.getJSONObject(position)
        holder.binding.nameTextView.text = employee.getString("firstName")
        holder.binding.titleTextView.text = employee.getString("title")
        holder.binding.emailTextView.text = employee.getString("email")
        holder.binding.phoneTextView.text = employee.getString("phone")
        holder.binding.departmentTextView.text = employee.getString("department")

        // Load image using Glide
        Glide.with(holder.binding.employeeImage.context)
            .load(employee.getString("image"))
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.employeeImage)
    }

    override fun getItemCount(): Int {
        return employees.length()
    }
}
