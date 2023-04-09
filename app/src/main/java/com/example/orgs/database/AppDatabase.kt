package com.example.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.orgs.database.converter.Converters
import com.example.orgs.database.dao.ProdutoDao
import com.example.orgs.model.Produto

@Database(entities = [Produto::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun produtoDao(): ProdutoDao

  companion object {
    @Volatile
    private var db: AppDatabase? = null
    fun instancia(context: Context): AppDatabase {
      return db ?: Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "orgs.db"
      ).build()
        .also {
          db = it
        }
    }
  }
}