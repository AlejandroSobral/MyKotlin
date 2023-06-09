package com.utn.firstapp.database


import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.utn.firstapp.R
import com.utn.firstapp.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader


class StartingUsers(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingUsers", "Pre-populating database...")
            fillWithStartingUsers(context)
        }
    }

    private fun fillWithStartingUsers(context: Context) {
        val dao = AppDatabase.getInstance(context)?.userDao()

        try {
            val users = loadJSONArray(context)
            for (i in 0 until users.length()) {
                val item = users.getJSONObject(i)
                val user = User(
                    id = "",
                    email = item.getString("email"),
                    password = item.getString("password")
                )

                dao?.insertUser(user)
            }


        } catch (e: JSONException) {
            Log.e("fillWithStartingNotes", e.toString())
        }
    }

    private fun loadJSONArray(context: Context): JSONArray {
        val inputStream = context.resources.openRawResource(R.raw.users)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }
}
