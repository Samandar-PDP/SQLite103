package uz.digital.sqlitedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import uz.digital.sqlitedatabase.database.MyDatabase
import uz.digital.sqlitedatabase.databinding.ActivityMainBinding
import uz.digital.sqlitedatabase.model.Student
import uz.digital.sqlitedatabase.util.toast

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val myDatabase by lazy { MyDatabase(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val name = binding.editTextTextPersonName.text.toString().trim()
            val lastName = binding.editTextTextPersonName2.text.toString().trim()
            if (name.isNotBlank() && lastName.isNotBlank()) {
                val student = Student(name = name, lastName = lastName)
                myDatabase.saveStudent(student)
                toast("Successfully saved")
                binding.editTextTextPersonName.text?.clear()
                binding.editTextTextPersonName2.text?.clear()
                updateText()
            }
        }
        updateText()

        binding.btnGet.setOnClickListener {
            val id = binding.editId.text.toString().trim().toInt()
            val student = myDatabase.getStudentById(id)
            binding.editTextTextPersonName.setText(student.name)
            binding.editTextTextPersonName2.setText(student.lastName)
        }
        binding.btnDelete.setOnClickListener {
            val id = binding.editId.text.toString().trim().toInt()
            myDatabase.deleteStudent(id)
            toast("Deleted")
            updateText()
        }
        binding.btnUpdate.setOnClickListener {
            val id = binding.editId.text.toString().trim().toInt()
            val name = binding.editTextTextPersonName.text.toString().trim()
            val lastName = binding.editTextTextPersonName2.text.toString().trim()
            myDatabase.updateStudent(Student(id = id, name = name, lastName = lastName))
            toast("Updated")
            updateText()
        }
    }
    private fun updateText() {
        val stringBuilder = StringBuilder()
        myDatabase.getAllStudents().forEach {
            stringBuilder.append(it.toString()).append("\n")
        }
        binding.textName.text = stringBuilder
    }
}