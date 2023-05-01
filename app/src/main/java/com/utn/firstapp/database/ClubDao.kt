package com.utn.firstapp.database



import androidx.room.*
import com.utn.firstapp.entities.Club
import com.utn.firstapp.entities.User


@Dao
interface ClubDao {

    @Query("SELECT * FROM clubs ORDER BY id")
    fun fetchAllClubs(): MutableList<Club?>?

    @Query("SELECT * FROM clubs ORDER BY name")
    fun fetchAllClubsOrderByName(): MutableList<Club?>?

    @Query("SELECT * FROM clubs WHERE id = :id")
    fun fetchClubById(id: Int): Club?

    @Query("SELECT * FROM clubs WHERE name = :name")
    fun fetchClubByName(name: String): Club?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertClub(club: Club)

    @Update
    fun updateClub(club: Club)

    @Delete
    fun delete(club: Club)
}
