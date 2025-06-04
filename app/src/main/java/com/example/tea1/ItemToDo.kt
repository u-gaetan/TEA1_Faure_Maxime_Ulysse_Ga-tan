package com.example.tea1

import java.io.Serializable

data class ItemToDo(
    private var description: String,
    private var fait: Boolean = false
) : Serializable {

    fun getDescription() = description
    fun setDescription(uneDescription: String) { description = uneDescription }

    fun getFait() = fait
    fun setFait(f: Boolean) { fait = f }

    override fun toString(): String {
        return "$description : ${if (fait) "fait" else "non fait"}"
    }
}

