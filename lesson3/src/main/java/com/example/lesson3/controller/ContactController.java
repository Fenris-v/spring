package com.example.lesson3.controller;

import com.example.lesson3.dto.ContactDto;
import com.example.lesson3.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping("/")
    public String getContactList(@NonNull Model model) {
        model.addAttribute("contacts", contactService.getContacts());
        return "list";
    }

    @PostMapping(value = "/contact", name = "app.contact.create")
    public String createContact(@Valid ContactDto contactDto, @NonNull BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("contact", contactDto);

            return "add-contact";
        }

        contactService.createContact(contactDto);
        return "redirect:/";
    }

    @GetMapping(value = "/contact", name = "app.contact.add")
    public String addNewContact(@NonNull Model model) {
        model.addAttribute("contact", new ContactDto());
        return "add-contact";
    }

    @GetMapping(value = "/contact/{id}", name = "app.contact.edit")
    public String editContact(@PathVariable UUID id, @NonNull Model model) {
        model.addAttribute("contact", contactService.getContactById(id));
        model.addAttribute("contactId", id);
        return "add-contact";
    }

    @PutMapping(value = "/contact/{id}", name = "app.contact.update")
    public String updateContact(@Valid ContactDto contactDto, @PathVariable UUID id) {
        contactService.updateContact(contactDto, id);
        return "redirect:/";
    }

    @DeleteMapping(value = "/contact/{id}", name = "app.contact.delete")
    public String removeContact(@PathVariable UUID id) {
        contactService.removeContact(id);
        return "redirect:/";
    }
}
