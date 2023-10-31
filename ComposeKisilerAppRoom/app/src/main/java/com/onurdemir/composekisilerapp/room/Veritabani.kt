package com.onurdemir.composekisilerapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.onurdemir.composekisilerapp.entity.Kisiler

@Database(entities = [Kisiler::class], version = 1)
abstract class Veritabani : RoomDatabase() {
    abstract fun kisilerDao() : KisilerDao

    companion object{
        var INSTANCE : Veritabani? = null

        fun veriTabaniErisim(context: Context) : Veritabani? {

            if (INSTANCE == null) {
                synchronized(Veritabani::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        Veritabani::class.java,
                        "rehber.sqlite").createFromAsset("rehber.sqlite").build()
                }
            }
            return INSTANCE
        }


    }

}