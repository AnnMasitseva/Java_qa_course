package ru.stqa.pft.addressbook.tests;


import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;


import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;



public class AddContactToGroupTests extends TestBase{
    @BeforeMethod
    public void ensurePreconditions(){
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
    public void testAddContactToGroup(){
        int contactId = 0;
        boolean completed = false;
        Groups beforeAddGroups = null;
        Groups beforeWithAddedGroups = null;
        Groups exitedGroups = app.db().groups();
        Contacts contacts = app.db().contacts();
        for(ContactData editedContact : contacts) {
            beforeAddGroups = editedContact.getGroups();
            GroupData newGroup =  app.contact().getGroupToAdd(exitedGroups, editedContact);
            if(newGroup != null) {
                app.contact().addContact(editedContact, newGroup);
                contactId = editedContact.getId();
                beforeWithAddedGroups = beforeAddGroups.withAdded(newGroup);
                completed = true;
                break;
            }
        }
        if (!completed) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
            Groups extendedGroups = app.db().groups();
            GroupData lastAddedGroup = extendedGroups.stream().max((g1, g2) -> Integer.compare(g1.getId(), g2.getId())).get();
            ContactData contact = contacts.iterator().next();
            contactId = contact.getId();
            app.contact().addContact(contact, lastAddedGroup);
            beforeWithAddedGroups = beforeAddGroups.withAdded(lastAddedGroup);
        }
        Groups groupAfter = app.db().contactById(contactId).getGroups();
        assertThat(groupAfter, equalTo(beforeWithAddedGroups));
    }
}
