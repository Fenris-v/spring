package com.example.lesson3.service;

import com.example.lesson3.dto.ContactDto;
import com.example.lesson3.jooq.db.tables.records.ContactsRecord;
import com.example.lesson3.mapper.ContactMapper;
import com.example.lesson3.model.Contact;
import com.example.lesson3.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.Result;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;

    public void createContact(ContactDto contactDto) {
        Contact contact = contactMapper.mapContactDtoToContact(contactDto);
        contact.setId(UUID.randomUUID());
        contactRepository.create(contact);
    }

    public List<Contact> getContacts() {
        Result<ContactsRecord> results = contactRepository.all();
        return contactMapper.mapResultsToContact(results);
    }

    public void removeContact(UUID id) {
        contactRepository.removeById(id);
    }

    public void updateContact(@NonNull ContactDto contactDto, UUID id) {
        contactRepository.update(contactDto, id);
    }

    public Contact getContactById(UUID id) {
        ContactsRecord record = contactRepository.findById(id);
        return contactMapper.mapRecordToContact(record);
    }
}
