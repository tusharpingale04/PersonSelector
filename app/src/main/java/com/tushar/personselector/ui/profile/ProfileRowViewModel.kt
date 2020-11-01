package com.tushar.personselector.ui.profile

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tushar.personselector.model.user.PersonDetail

/**
 * This ViewModel holds data of each recyclerview item
 */
class ProfileRowViewModel: ViewModel(){
    val item = ObservableField<PersonDetail>()
}