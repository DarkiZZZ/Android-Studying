package ru.msokolov.shoppinglistsolid.presentation

import androidx.lifecycle.ViewModel
import ru.msokolov.data.ShopListRepositoryImpl
import ru.msokolov.domain.DeleteShopItemUseCase
import ru.msokolov.domain.EditShopItemUseCase
import ru.msokolov.domain.GetShopListUseCase
import ru.msokolov.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }
}
