package uz.digital.sqlitedatabase.database

import uz.digital.sqlitedatabase.model.Student

interface DatabaseService {
    fun saveStudent(student: Student)
    fun getAllStudents(): List<Student>
    fun updateStudent(student: Student)
    fun getStudentById(id: Int): Student
    fun deleteStudent(id: Int)
    fun deleteAllStudents()
    fun searchByName(name: String)
}