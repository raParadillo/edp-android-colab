package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    // TODO 2: add the annotation for this SQL, on the line below:
    //   SELECT * FROM posts ORDER BY created_at DESC
    @Query("SELECT * FROM posts ORDER BY created_at DESC")
    fun observeAll(): Flow<List<Post>>

    // TODO 3: add a new post and return its new id
    @Insert
    suspend fun insert(post: Post): Long

    // TODO 4: save the changes to an existing post
    @Update
    suspend fun update(post: Post)

    // TODO 5: remove a post
    @Delete
    suspend fun delete(post: Post)
}
