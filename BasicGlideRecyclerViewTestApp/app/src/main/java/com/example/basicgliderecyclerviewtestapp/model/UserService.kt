package com.example.basicgliderecyclerviewtestapp.model

import com.example.basicgliderecyclerviewtestapp.UserNotFoundException
import com.example.basicgliderecyclerviewtestapp.tasks.SimpleUserTask
import com.example.basicgliderecyclerviewtestapp.tasks.UserTask
import com.github.javafaker.Faker
import java.util.*
import java.util.concurrent.Callable

typealias UsersListener = (users: List<User>) -> Unit

class UserService {

    private var users = mutableListOf<User>()
    private val listeners = mutableSetOf<UsersListener>()
    private var loaded = false


    fun loadUsers(): UserTask<Unit> = SimpleUserTask(Callable {
        Thread.sleep(2000) // <-- Imitation of hard work
        val faker = Faker.instance()
        IMAGES_URL_LIST.shuffle()
        users = (1..100).map { User(
            id = it.toLong(),
            name = faker.name().fullName(),
            company = faker.company().name(),
            photo = IMAGES_URL_LIST[it % IMAGES_URL_LIST.size]
        ) }.toMutableList()
        loaded = true
        notifyChanges()
    })

    fun getUserDetailsById(id: Long): UserTask<UserDetails> = SimpleUserTask(Callable {
        Thread.sleep(2000)
        val user: User = users.firstOrNull { it.id == id } ?: throw UserNotFoundException()
        return@Callable UserDetails(
            user,
            details = Faker.instance().lorem().paragraphs(3).joinToString("\n")
        )
    })

    fun deleteUser(user: User) : UserTask<Unit> = SimpleUserTask(Callable {
        Thread.sleep(2000)
        val deleteIndex = users.indexOfFirst { it.id == user.id }
        if (deleteIndex != -1){
            users.removeAt(deleteIndex)
        }
        notifyChanges()
    })


    // relocation shows what direction you need to
    // relocate user: variable "relocation" must be : 1(up), -1(down), 0(stay)
    fun relocateUser(user: User, relocation: Int) : UserTask<Unit> = SimpleUserTask(Callable {
        Thread.sleep(2000)
        val oldIndex = users.indexOfFirst { it.id == user.id }
        if (oldIndex == -1) return@Callable
        val newIndex = oldIndex + relocation
        if (newIndex < 0 || newIndex >= users.size) return@Callable
        Collections.swap(users, oldIndex, newIndex)
        notifyChanges()
    })

    fun addListener(listener: UsersListener){
        listeners.add(listener)
        if (loaded)  listener.invoke(users)
    }

    fun removeListener(listener: UsersListener){
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        if (!loaded) return
        listeners.forEach { it.invoke(users) }
    }

    companion object{
        private val IMAGES_URL_LIST = mutableListOf<String>(
            "https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
            "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80",
            "https://images.unsplash.com/photo-1527980965255-d3b416303d12?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80",
            "https://images.unsplash.com/photo-1580489944761-15a19d654956?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=761&q=80",
            "https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80",
            "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80",
            "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80",
            "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
            "https://images.unsplash.com/photo-1629467057571-42d22d8f0cbd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=698&q=80",
            "https://images.unsplash.com/photo-1628157588553-5eeea00af15c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80",
            "https://images.unsplash.com/photo-1639149888905-fb39731f2e6c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=764&q=80",
            "https://images.unsplash.com/photo-1558898479-33c0057a5d12?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80",
            "https://images.unsplash.com/photo-1623330188314-8f4645626731?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=717&q=80",
            "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
        )
    }
}