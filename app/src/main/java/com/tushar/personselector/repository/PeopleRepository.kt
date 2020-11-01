package com.tushar.personselector.repository

import android.util.Log
import com.tushar.personselector.db.PersonDao
import com.tushar.personselector.model.user.PersonDetail
import com.tushar.personselector.model.user.PersonDetailsResponse
import com.tushar.personselector.network.ProfilesApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

const val TAG = "PeopleRepository"

@ExperimentalCoroutinesApi
class PeopleRepository @Inject constructor(
    private val personService: ProfilesApiService,
    private val personDao: PersonDao
) {

    fun fetchProfiles(): Flow<Resource<List<PersonDetail>>> {
        return object : NetworkBoundResource<List<PersonDetail>, PersonDetailsResponse>(){
            override suspend fun saveNetworkResult(resp: PersonDetailsResponse) {
                personDao.insertPeople(resp.results)
            }

            override fun shouldFetch(data: List<PersonDetail>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): Flow<List<PersonDetail>> {
                return personDao.getPeople()
            }

            override suspend fun fetchFromNetwork(): Response<PersonDetailsResponse> {
                return personService.fetchPersonList(results = 10)
            }

            override fun onFetchFailed(message: String?) {
                Log.d(TAG, "onFetchFailed: ${message ?: "Empty Message"}")
            }

        }.asFlow()
    }

    suspend fun updatePerson(personDetail: PersonDetail) {
        personDao.updatePerson(personDetail.isAccepted ?: false, personDetail.email)
    }
}