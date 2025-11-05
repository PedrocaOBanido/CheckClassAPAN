package com.opus.checkclassapan.data

data class Student(val id: String, val name: String, val isPresent: Boolean = false)

val students = listOf(
    Student("123", "Fulano de Tal"),
    Student("456", "Ciclano de Tal"),
    Student("789", "Beltrano de Tal")
)
