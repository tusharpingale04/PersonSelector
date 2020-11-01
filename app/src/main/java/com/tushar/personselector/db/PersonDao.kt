package com.tushar.personselector.db

import androidx.room.*
import com.tushar.personselector.model.user.PersonDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPeople(people: List<PersonDetail>)

  @Query("UPDATE person SET isAccepted = :isAccepted WHERE email = :id_ ")
  suspend fun updatePerson(isAccepted: Boolean,id_: String ): Int

  @Query("SELECT * FROM person ")
  fun getPeople(): Flow<List<PersonDetail>>
}
