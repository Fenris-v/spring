package com.example.lesson3.repository;

import com.example.lesson3.dto.ContactDto;
import com.example.lesson3.exception.NotFoundException;
import com.example.lesson3.jooq.db.tables.records.ContactsRecord;
import com.example.lesson3.model.Contact;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static com.example.lesson3.jooq.db.tables.Contacts.CONTACTS;

@Repository
@RequiredArgsConstructor
public class ContactRepository {
    private final DSLContext dsl;

    public void create(Contact contact) {
        dsl.newRecord(CONTACTS, contact).store();
    }

    public void update(ContactDto contactDto, UUID id) {
        dsl
                .update(CONTACTS)
                .set(CONTACTS.NAME, contactDto.getName())
                .set(CONTACTS.LASTNAME, contactDto.getLastname())
                .set(CONTACTS.PHONE, contactDto.getPhone())
                .set(CONTACTS.EMAIL, contactDto.getEmail())
                .where(CONTACTS.ID.eq(id))
                .execute();
    }

    public Result<ContactsRecord> all() {
        return dsl.selectFrom(CONTACTS).fetch();
    }

    public void removeById(UUID id) {
        dsl.deleteFrom(CONTACTS).where(CONTACTS.ID.eq(id)).execute();
    }

    public ContactsRecord findById(UUID id) {
        return dsl.selectFrom(CONTACTS).where(CONTACTS.ID.eq(id)).fetchOptional()
                  // Это надо бы поймать в AdviceController, но немножко лень на таком простом задании 😊
                  .orElseThrow(NotFoundException::new);
    }
}
