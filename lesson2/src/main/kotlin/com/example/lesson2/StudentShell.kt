package com.example.lesson2

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import java.util.UUID

@ShellComponent
class StudentShell(private val studentMap: MutableMap<UUID, Student> = HashMap()) {
    companion object {
        private const val ANSI_RESET: String = "\u001B[0m"
        private const val ANSI_GREEN: String = "\u001B[32m"
    }

    @ShellMethod(key = ["add"], value = "Add a student")
    fun addStudent(
        @ShellOption(value = ["n"]) name: String,
        @ShellOption(value = ["l"]) lastname: String,
        @ShellOption(value = ["a"]) age: Int
    ): String {
        val student = Student(name, lastname, age)
        studentMap[student.id] = student
        return ANSI_GREEN + "Student \"$student\" added" + ANSI_RESET
    }

    @ShellMethod(key = ["remove"], value = "Remove student by id")
    fun removeStudent(id: UUID): String {
        studentMap.remove(id)
        return ANSI_GREEN + "Student with id \"$id\" deleted" + ANSI_RESET
    }

    @ShellMethod(key = ["clear"], value = "Remove all students")
    fun removeAllStudents(): String {
        studentMap.clear()
        return ANSI_GREEN + "Student list reset" + ANSI_RESET
    }

    @ShellMethod(key = ["list"], value = "Print student list")
    fun printStudents(): String {
        val stringBuilder: StringBuilder = StringBuilder().append(ANSI_GREEN)
        for ((index: Int, student: Student) in studentMap.values.withIndex()) {
            stringBuilder.append(student.toString())
            if (index < studentMap.size - 1) {
                stringBuilder.appendLine()
            }
        }

        return stringBuilder.append(ANSI_RESET).toString()
    }
}
