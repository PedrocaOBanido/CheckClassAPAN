package com.opus.checkclassapan.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.opus.checkclassapan.data.dao.ClassDao
import com.opus.checkclassapan.data.model.SchoolClass
import com.opus.checkclassapan.data.model.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [SchoolClass::class, Student::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun classDao(): ClassDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .addCallback(DatabaseSeeder(context))
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

private class DatabaseSeeder(private val context: Context) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(context)
            val classDao = database.classDao()

            val schoolClass = SchoolClass(name = "3º ano A")
            val classId = classDao.insertClass(schoolClass)

            val students = listOf(
                Student(name = "João", classId = classId.toInt()),
                Student(name = "Maria", classId = classId.toInt()),
                Student(name = "José", classId = classId.toInt())
            )
            classDao.insertStudents(students)
        }
    }
}
