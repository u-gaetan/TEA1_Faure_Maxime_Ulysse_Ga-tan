package com.example.tea1

import java.io.Serializable

data class ListeToDo(
    private var titreListeToDo: String
) : Serializable {

    private val lesItems = mutableListOf<ItemToDo>()

    fun setTitreListeToDo(titre: String) {
        titreListeToDo = titre
    }

    fun getTitreListeToDo() = titreListeToDo

    fun getLesItems() = lesItems

    fun setLesItems(items: List<ItemToDo>) {
        lesItems.clear()
        lesItems.addAll(items)
    }

    fun rechercheItem(descriptionItem: String): ItemToDo? {
        return lesItems.find { it.getDescription() == descriptionItem }
    }

    override fun toString(): String {
        return "$titreListeToDo : $lesItems"
    }
}
