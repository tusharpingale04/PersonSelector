package com.tushar.personselector.model.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "person")
data class PersonDetail(
        @PrimaryKey
        @SerializedName("email") val email: String,
        @SerializedName("login") val login: Login,
        @SerializedName("gender") val gender: String,
        @SerializedName("name") val name: Name,
        @SerializedName("location") val location: Location,
        @SerializedName("picture") val picture: Picture,
        @SerializedName("dob") val dob: Dob,
        var isAccepted: Boolean? = null
) : Parcelable