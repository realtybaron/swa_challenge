package com.android.net

// To parse the JSON, install Klaxon and do:
//
//   val randomUsers = RandomUsers.fromJson(jsonString)

import android.os.Parcelable
import com.beust.klaxon.Klaxon
import kotlinx.android.parcel.Parcelize

private val klaxon = Klaxon()

data class RandomUsers(
    val info: Info,
    val results: List<RandomUser>
) {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<RandomUsers>(json)
    }
}

data class Info(
    val page: Long,
    val seed: String,
    val results: Long,
    val version: String
)

@Parcelize
data class RandomUser(
    val id: ID,
    val dob: Dob,
    val nat: String,
    val cell: String,
    val name: Name,
    val email: String,
    val login: Login,
    val phone: String,
    val gender: String,
    val picture: Picture,
    val location: Location,
    val registered: Dob
) : Parcelable

@Parcelize
data class Dob(
    val age: Long,
    val date: String
) : Parcelable

@Parcelize
data class ID(
    val name: String,
    val value: String
) : Parcelable

@Parcelize
data class Location(
    val city: String,
    val state: String,
    val street: String,
    val postcode: String,
    val timezone: Timezone,
    val coordinates: Coordinates
) : Parcelable

@Parcelize
data class Coordinates(
    val latitude: String,
    val longitude: String
) : Parcelable

@Parcelize
data class Timezone(
    val offset: String,
    val description: String
) : Parcelable

@Parcelize
data class Login(
    val md5: String,
    val sha1: String,
    val salt: String,
    val uuid: String,
    val sha256: String,
    val username: String,
    val password: String
) : Parcelable

@Parcelize
data class Name(
    val last: String,
    val first: String,
    val title: String
) : Parcelable

@Parcelize
data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
) : Parcelable
