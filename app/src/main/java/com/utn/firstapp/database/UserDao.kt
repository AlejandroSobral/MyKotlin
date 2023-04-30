package com.utn.firstapp.database


import androidx.room.*
import com.utn.firstapp.entities.User


@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id")
    fun fetchAllUsers(): MutableList<User?>?

    @Query("SELECT * FROM users WHERE id = :id")
    fun fetchUserById(id: Int): User?

    @Query("SELECT * FROM users WHERE (name = :user and password = :password)")
    fun fetchUserByUserAndPass(user: String, password: String): User?

    @Query("SELECT * FROM users WHERE (name = :user and email = :email)")
    fun fetchUserByUserAndMail(user: String, email: String): User?

    @Query("SELECT * FROM users WHERE (name = :user)")
    fun fetchUserByUserName(user: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun delete(user: User)
}
