package com.example.lesson1

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.Collections
import java.util.StringTokenizer

class ContactService(private val path: Path) {
    private val contacts: MutableMap<String, Contact> = HashMap()
    private lateinit var writer: BufferedWriter
    private lateinit var reader: BufferedReader
    private var isRun = true

    fun handle() {
        checkFile()
        readExistsContacts()
        listenInput()
    }

    private fun checkFile() {
        if (Files.notExists(path)) {
            Files.createFile(path)
        }
    }

    private fun readExistsContacts() {
        Files.lines(path).use { lines ->
            var contact: Contact
            lines.forEach {
                val stringTokenizer = StringTokenizer(it, ";")
                contact = Contact(
                    stringTokenizer.nextToken().trim(),
                    stringTokenizer.nextToken().trim(),
                    stringTokenizer.nextToken().trim()
                )

                contacts[contact.email] = contact
            }
        }
    }

    private fun listenInput() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            this.reader = reader
            BufferedWriter(OutputStreamWriter(System.out)).use {
                writer = it
                var command: String
                while (isRun) {
                    printInfo()
                    command = reader.readLine().trim()
                    when (command) {
                        Command.LIST.value -> printContactList()
                        Command.ADD.value -> addContact()
                        Command.REMOVE.value -> removeContactByEmail()
                        Command.EXIT.value -> exit()
                        else -> print("Проверьте команду")
                    }
                }
            }
        }
    }

    private fun printContactList() {
        val stringBuilder = StringBuilder()
        for ((index, contact) in contacts.values.withIndex()) {
            stringBuilder.append(contact.name + "|" + contact.number + "|" + contact.email)
            if (index < contacts.size - 1) {
                stringBuilder.appendLine()
            }
        }

        print(stringBuilder.toString())
    }

    private fun addContact() {
        print(
            "Для добавления контакта введите данные в следующем формате:",
            "Иванов Иван Иванович; +890999999; someEmail@example.example"
        )

        val stringTokenizer = StringTokenizer(reader.readLine(), ";")
        val contact = Contact()

        try {
            contact.name = stringTokenizer.nextToken().trim()
            contact.number = stringTokenizer.nextToken().trim()
            contact.email = stringTokenizer.nextToken().trim()
        } catch (ex: NoSuchElementException) {
            print("Невалидный ввод, проверьте формат ввода")
            return
        }

        if (contacts.contains(contact.email)) {
            print("Контакт уже существует")
            return
        }

        contacts[contact.email] = contact
        Files.newBufferedWriter(path, StandardOpenOption.APPEND, StandardOpenOption.WRITE).use {
            it.write(contact.toString())
            it.newLine()
            it.flush()
        }
    }

    private fun removeContactByEmail() {
        val email: String = reader.readLine()
        if (!contacts.contains(email)) {
            print("Контакт не существует")
            return
        }

        contacts.remove(email)
        Files.write(path, Collections.emptyList(), StandardOpenOption.TRUNCATE_EXISTING)
        Files.newBufferedWriter(path, StandardOpenOption.APPEND, StandardOpenOption.WRITE).use {
            for (contact: Contact in contacts.values) {
                it.write(contact.toString())
                it.newLine()
            }

            it.flush()
        }
    }

    private fun exit() {
        print("Goodbye!")
        isRun = false
    }

    private fun print(vararg strings: String) {
        for (string: String in strings) {
            writer.write(string)
            writer.newLine()
        }

        writer.newLine()
        writer.flush()
    }


    private fun printInfo() {
        print(
            "Для вывода списка существующих контактов введите команду: " + Command.LIST.value,
            "Для добавления контакта введите команду: " + Command.ADD.value,
            "Для удаления контакта введите команду: " + Command.REMOVE.value,
            "Для выхода из приложения введите команду: " + Command.EXIT.value
        )
    }
}
