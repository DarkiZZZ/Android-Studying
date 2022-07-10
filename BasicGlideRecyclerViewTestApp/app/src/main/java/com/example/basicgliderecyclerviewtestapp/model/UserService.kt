package com.example.basicgliderecyclerviewtestapp.model

import com.github.javafaker.Faker

class UserService {

    private var users = mutableListOf<User>()
    init {
        val faker = Faker.instance()
        IMAGES_URL_LIST.shuffle()
        val createdUsers = (1..100).map { User(
            id = it.toLong(),
            name = faker.name().fullName(),
            company = faker.company().name(),
            photo = IMAGES_URL_LIST[it % IMAGES_URL_LIST.size]
        ) }

    }

    companion object{
        private val IMAGES_URL_LIST = mutableListOf<String>()
    }
}