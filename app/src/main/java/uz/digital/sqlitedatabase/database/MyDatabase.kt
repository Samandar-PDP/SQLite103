package uz.digital.sqlitedatabase.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.digital.sqlitedatabase.model.Student
import uz.digital.sqlitedatabase.util.Constants.DB_NAME
import uz.digital.sqlitedatabase.util.Constants.ID
import uz.digital.sqlitedatabase.util.Constants.LAST_NAME
import uz.digital.sqlitedatabase.util.Constants.NAME
import uz.digital.sqlitedatabase.util.Constants.TABLE_NAME

class MyDatabase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1), DatabaseService {
    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, $NAME TEXT NOT NULL, $LAST_NAME TEXT NOT NULL) "
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    override fun saveStudent(student: Student) { // Said'alo Mo'minov
        val database = this.writableDatabase
        val contentValues = ContentValues() // karobka:  name -> Saidalo, lastName: Mo'minov
        contentValues.put(NAME, student.name)
        contentValues.put(LAST_NAME, student.lastName)
        database.insert(TABLE_NAME, null, contentValues) // saqlash
        database.close()
    }

    override fun getAllStudents(): List<Student> {
        val database = this.readableDatabase
        val studentList = mutableListOf<Student>()
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val student = Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
                studentList.add(student)
            } while (cursor.moveToNext())
        }
        return studentList
    }

    override fun updateStudent(student: Student) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, student.id)
        contentValues.put(NAME, student.name)
        contentValues.put(LAST_NAME, student.lastName)
        database.update(
            TABLE_NAME,
            contentValues,
            "$ID = ?",
            arrayOf(student.id.toString())
        ) // saidalo mominov 1 -> muhammadrizo polonchiyev 1
        database.close()
    }

    override fun getStudentById(id: Int): Student {
        val database = this.readableDatabase
        val cursor = database.query(
            TABLE_NAME,
            arrayOf(ID, NAME, LAST_NAME),
            "$ID = ?",
            arrayOf(id.toString()),
            null,
            null    ,
            null
        )
        cursor?.moveToFirst()
        return Student(cursor?.getInt(0), cursor?.getString(1), cursor?.getString(2))
    }

    override fun deleteStudent(id: Int) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$ID = ?", arrayOf(id.toString()))
        database.close()
    }

    override fun deleteAllStudents() {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, null, null)
        database.close()
    }

    override fun searchByName(name: String) {
        val db = this.writableDatabase

    }
}