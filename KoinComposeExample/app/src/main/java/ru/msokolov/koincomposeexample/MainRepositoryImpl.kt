package ru.msokolov.koincomposeexample

class MainRepositoryImpl(
    private val api: MyApi
): MainRepository {

    override fun doNetworkCall() {
        api.callApi()
    }
}