package com.example.studentmanagermvcandrxjava.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studentmanagermvcandrxjava.model.local.student.Student
import com.example.studentmanagermvcandrxjava.model.local.student.StudentDao

@Database(entities = [Student::class] , version = 1 , exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract val studentDao: StudentDao

    companion object {

        @Volatile
        private var database: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {

            synchronized(this) {
                if (database == null) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "myDatabase.db"
                    ).build()
                }
                    return database!!
            }
        }

    }
}