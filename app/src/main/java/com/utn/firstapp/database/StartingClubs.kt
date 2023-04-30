package com.utn.firstapp.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.utn.firstapp.R
import com.utn.firstapp.entities.Club
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader


class StartingClubs(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingClubs", "Pre-populating database...")
            fillWithStartingClubs(context)
        }
    }

    private fun fillWithStartingClubs(context: Context) {
        val dao = AppDatabase.getInstance(context)?.clubDao()

        try {
            val clubs = loadJSONArray(context)
            for (i in 0 until clubs.length()) {
                val item = clubs.getJSONObject(i)
                val club = Club(
                    id = 0,
                    name = item.getString("Club"),
                    founded = item.getString("Founded"),
                    country = item.getString("Country"),
                    nickname = item.getString("Nick"),
                    imageURL = item.getString("imageURL"),
                    league = item.getString("League")
                )

                dao?.insertClub(club)
            }


        } catch (e: JSONException) {
            Log.e("fillWithStartingClubs", e.toString())
        }
    }

    private fun loadJSONArray(context: Context): JSONArray {
        val inputStream = context.resources.openRawResource(R.raw.clubes)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }
}
