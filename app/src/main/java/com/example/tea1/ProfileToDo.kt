package com.example.tea1

import java.io.Serializable

data class ProfileToDo(
    private var login: String
) : Serializable {

    private val mesListeToDo = mutableListOf<ListeToDo>()

    fun getLogin() = login
    fun setLogin(unLogin: String) { login = unLogin }

    fun getMesListeToDo() = mesListeToDo

    fun setMesListeToDo(liste: List<ListeToDo>) {
        mesListeToDo.clear()
        mesListeToDo.addAll(liste)
    }

    fun ajouteListe(uneListe: ListeToDo) {
        mesListeToDo.add(uneListe)
    }

    override fun toString(): String {
        return "$login : $mesListeToDo"
    }
}
