package com.tushar.personselector.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tushar.personselector.model.user.PersonDetail
import com.tushar.personselector.repository.PeopleRepository
import com.tushar.personselector.repository.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ProfileViewModel @ViewModelInject constructor(
    private val repository: PeopleRepository
): ViewModel() {

    private val _personsLiveData = MutableLiveData<Resource<List<PersonDetail>>>()
    val personsLiveData: LiveData<Resource<List<PersonDetail>>>
        get() = _personsLiveData

    /**
     * Fetches user profiles from Repository
     */
    fun fetchProfiles(){
        viewModelScope.launch {
            repository.fetchProfiles().collect{
                _personsLiveData.value = it
            }
        }
    }

    /**
     * @param personDetail object to update in DB
     */
    fun updatePerson(personDetail: PersonDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePerson(personDetail)
        }
    }

}