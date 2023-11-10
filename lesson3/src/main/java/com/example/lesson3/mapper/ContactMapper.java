package com.example.lesson3.mapper;

import com.example.lesson3.dto.ContactDto;
import com.example.lesson3.jooq.db.tables.records.ContactsRecord;
import com.example.lesson3.model.Contact;
import lombok.RequiredArgsConstructor;
import org.jooq.Result;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.lesson3.jooq.db.tables.Contacts.CONTACTS;

@Component
@RequiredArgsConstructor
public class ContactMapper {
    private final ModelMapper modelMapper;

    public Contact mapContactDtoToContact(ContactDto contactDto) {
        return modelMapper.map(contactDto, Contact.class);
    }

    public List<Contact> mapResultsToContact(@NonNull Result<ContactsRecord> results) {
        return results.map(record -> Contact
                .builder()
                .id(record.get(CONTACTS.ID))
                .name(record.get(CONTACTS.NAME))
                .lastname(record.get(CONTACTS.LASTNAME))
                .email(record.get(CONTACTS.EMAIL))
                .phone(record.get(CONTACTS.PHONE))
                .build());
    }

    public Contact mapRecordToContact(@NonNull ContactsRecord record) {
        return Contact
                .builder()
                .id(record.get(CONTACTS.ID))
                .name(record.get(CONTACTS.NAME))
                .lastname(record.get(CONTACTS.LASTNAME))
                .email(record.get(CONTACTS.EMAIL))
                .phone(record.get(CONTACTS.PHONE))
                .build();
    }
}
