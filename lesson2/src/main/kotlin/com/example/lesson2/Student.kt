package com.example.lesson2

import java.util.UUID

class Student(
    private val firstName: String,
    private val lastname: String,
    private val age: Int
) {
    val id: UUID = UUID.randomUUID()

    override fun toString(): String {
        return "$id - $firstName $lastname: $age years old"
    }
}
