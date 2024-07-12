package com.example.e03builduiwithlayouteditor2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val firstnames = arrayOf("Renato", "Rosangela", "Tim", "Bartol", "Jeannette")
    private val lastnames = arrayOf("Ksenia", "Metzli", "Asuncion", "Zemfina", "Giang")
    private val jobtitles = arrayOf(
        "District Quality Coordinator",
        "International Intranet Representative",
        "District Intranet Administrator",
        "Dynamic Research Manager",
        "Central Infrastructure Consultant"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showEmployeeData(0)
    }

    fun numberClicked(view: View) {
        val tag = view.tag
        if (tag != null) {
            val number = tag.toString().toInt()
            showEmployeeData(number - 1)
        }
    }


    fun showEmployeeData(index: Int) {
        val firstnameTextView = findViewById<TextView>(R.id.firstnameTextView)
        val lastnameTextView = findViewById<TextView>(R.id.lastnameTextView)
        val jobtitleTextView = findViewById<TextView>(R.id.jobtitleTextView)
        val employeeInfoTextView = findViewById<TextView>(R.id.employeeInfoTextView)
        firstnameTextView.text = firstnames[index]
        lastnameTextView.text = lastnames[index]
        jobtitleTextView.text = jobtitles[index]
        employeeInfoTextView.text =
            getString(R.string.employee_info_text, lastnames[index], firstnames[index], getString(R.string.basic_text))
        val imageView = findViewById<ImageView>(R.id.imageView)
        val resourceId = when (index) {
            0 -> R.drawable.employee1
            1 -> R.drawable.employee2
            2 -> R.drawable.employee3
            3 -> R.drawable.employee4
            4 -> R.drawable.employee5
            else -> R.drawable.employee1
        }
        imageView.setImageResource(resourceId)
    }
}
