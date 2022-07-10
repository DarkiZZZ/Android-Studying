package com.example.basicgliderecyclerviewtestapp.model

import com.github.javafaker.Faker
import java.util.*

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

    fun getUser(): List<User>{
        return users
    }

    fun deleteUser(user: User){
        val deleteIndex = users.indexOfFirst { it.id == user.id }
        if (deleteIndex != -1){
            users.removeAt(deleteIndex)
        }
    }
                    // relocation shows what direction you need to
                    // relocate user: 1(up), -1(down), 0(stay)
    fun relocateUser(user: User, relocation: Int){
        val oldIndex = users.indexOfFirst { it.id == user.id }
        val newIndex = oldIndex + relocation
        if (newIndex < 0 || newIndex >= users.size){
            Collections.swap(users, oldIndex, newIndex)
        }
    }

    companion object{
        private val IMAGES_URL_LIST = mutableListOf<String>()
    }
}