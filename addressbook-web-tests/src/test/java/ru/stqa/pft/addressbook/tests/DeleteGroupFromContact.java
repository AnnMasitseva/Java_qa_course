package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteGroupFromContact extends TestBase {
    @BeforeMethod
    public void ensurePreconditions() {
        if(app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        Groups groups = app.db().groups();
        if(app.db().contacts().size() == 0) {
            app.goTo().homePage();
            File photo = new File("src/test/resources/cat.png");
            app.contact().create(new ContactData().withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva")
                    .withAddress("St.Peterburg, Blan-Menilskaya str., 1, kv. 29").withMobilePhone("+79009009090").withEmail("email@domain.com").withPhoto(photo));
        } else {
            ContactData contact= app.db().contacts().iterator().next();
            if(contact.getGroups().size() == 0) {
                contact.inGroup(groups.iterator().next());
            }
        }
    }
    @Test
    public void testDeletionContactFromGroup() {
        Contacts beforeContacts = app.db().groupForRemoveContact().getContacts();
        int situatedGroupIDForRemoveContact = app.db().groupForRemoveContact().getId();
        app.goTo().homePage();
        app.contact().contactsFilterByGroup(situatedGroupIDForRemoveContact);
        ContactData selectedContact = beforeContacts.iterator().next();
        app.contact().selectContactById(selectedContact.getId());
        app.contact().remove();
        app.goTo().homePage();

        Contacts afterContacts = app.db().getGroupFromDb(situatedGroupIDForRemoveContact).getContacts();
        assertThat(afterContacts, equalTo(beforeContacts.without(selectedContact)));
    }
}
