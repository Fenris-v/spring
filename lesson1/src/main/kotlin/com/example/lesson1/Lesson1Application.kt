package com.example.lesson1

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    val context: ApplicationContext = AnnotationConfigApplicationContext(AppConfig::class.java)
    val contactService: ContactService = context.getBean(ContactService::class.java)
    contactService.handle()
}
