package com.socotech.swa.net

// To parse the JSON, install Klaxon and do:
//
//   val randomUsers = RandomUsers.fromJson(jsonString)

import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

data class RandomUsers(
    val info: Info,
    val results: List<Result>
) {
    fun toJson() = klaxon.toJsonString(this)

    companion object {
        fun fromJson(json: String) = klaxon.parse<RandomUsers>(json)
    }
}

data class Info(
    val seed: String,
    val results: Long,
    val page: Long,
    val version: String
)

data class Result(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: Dob,
    val registered: Dob,
    val phone: String,
    val cell: String,
    val id: ID,
    val picture: Picture,
    val nat: String
)

data class Dob(
    val date: String,
    val age: Long
)

data class ID(
    val name: String,
    val value: String
)

data class Location(
    val street: String,
    val city: String,
    val state: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Timezone(
    val offset: String,
    val description: String
)

data class Login(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
)

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)