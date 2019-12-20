package com.orange_infinity.onlinepay.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.orange_infinity.onlinepay.entities.model.CashCheque

@Database(entities = [CashCheque::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCashChequeDao(): CashChequeDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "app-database.db")
                        .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}