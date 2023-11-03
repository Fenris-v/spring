package com.example.lesson1

class Contact(var name: String = "", var number: String = "", var email: String = "") {
    override fun toString(): String {
        return "$name;$number;$email"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contact
        return email == other.email
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }
}
