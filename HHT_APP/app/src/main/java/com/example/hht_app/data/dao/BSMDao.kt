package com.example.hht_app.data.dao

import androidx.room.Dao
import kotlinx.coroutines.flow.Flow
import androidx.room.*
import com.example.hht_app.data.model.BSM

@Dao
interface BSMDao {

    @Query("SELECT * FROM bsm ORDER BY bagTagNumber ASC")
    fun getAllBSM(): Flow<List<BSM>>

    @Query("SELECT * FROM bsm WHERE isProcessed = 0")
    fun getUnprocessedBSM(): Flow<List<BSM>>

    @Query("SELECT * FROM bsm WHERE bagTagNumber = :tagNumber")
    suspend fun getBSMByTagNumber(tagNumber: String): BSM?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBSM(bsm: BSM)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBSM(bsmList: List<BSM>)

    @Update
    suspend fun updateBSM(bsm: BSM)

    @Delete
    suspend fun deleteBSM(bsm: BSM)

    @Query("DELETE FROM bsm")
    suspend fun deleteAllBSM()
}