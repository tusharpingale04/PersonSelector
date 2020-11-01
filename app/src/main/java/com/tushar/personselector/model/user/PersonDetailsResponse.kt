package com.tushar.personselector.model.user

import com.google.gson.annotations.SerializedName
import com.tushar.personselector.model.NetworkResponseModel

/**
 * Base response of Person
 */
data class PersonDetailsResponse (
		@SerializedName("results")
		val results : List<PersonDetail>,
		@SerializedName("info")
		val info : Info
) : NetworkResponseModel