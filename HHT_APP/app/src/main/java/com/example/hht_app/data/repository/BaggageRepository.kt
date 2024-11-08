package com.example.hht_app.data.repository

import com.example.hht_app.data.model.BSM
import com.example.hht_app.data.dao.BSMDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaggageRepository @Inject constructor(
    private val bsmDao: BSMDao
) {
    fun getUnprocessedBSMList(): Flow<List<BSM>> {
        return bsmDao.getUnprocessedBSM()
    }

    val unprocessedBSM = bsmDao.getUnprocessedBSM()
    suspend fun processBaggage(tagNumber: String): Result<BSM> {
        return try {
            val bsm = bsmDao.getBSMByTagNumber(tagNumber)
            if (bsm != null) {
                val updatedBsm = bsm.copy(isProcessed = true)
                bsmDao.updateBSM(updatedBsm)
                Result.success(updatedBsm)
            } else {
                Result.failure(Exception("找不到此行李條碼"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun getBSMList(): Flow<List<BSM>> {
        return bsmDao.getAllBSM()
    }

    suspend fun getBSMByTagNumber(tagNumber: String): BSM? {
        return bsmDao.getBSMByTagNumber(tagNumber)
    }

    suspend fun insertBSM(bsm: BSM) {
        bsmDao.insertBSM(bsm)
    }

    suspend fun updateBSM(bsm: BSM) {
        bsmDao.updateBSM(bsm)
    }

    suspend fun insertBSMList(bsmList: List<BSM>) {
        bsmDao.insertAllBSM(bsmList)
    }

    suspend fun deleteAllBSM() {
        bsmDao.deleteAllBSM()
    }

    //insert dummy data for development
    suspend fun insertDummyData() {
        val dummyData = listOf(
            BSM("1234567890125", "CI102", "JFK", "Tony Stark"),
            BSM("1234567890124", "CI101", "NRT", "Peter Parker"),
            BSM("1234567890123", "CI100", "TPE", "John Doe"),
            BSM("2234567890122", "CI200", "HKG", "Jane Smith"),
            BSM("3234567890121", "CI300", "NRT", "Mary Johnson")
        )
        insertBSMList(dummyData)
    }

}