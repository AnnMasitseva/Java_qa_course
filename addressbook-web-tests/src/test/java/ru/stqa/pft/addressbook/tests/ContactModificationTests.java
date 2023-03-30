package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;


import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase{
    @BeforeMethod
    private void ensurePreconditions(){
        if ( app.db().contacts().size() == 0){
            if (app.db().groups().size() == 0){
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("test2"));
            }
            app.goTo().homePage();
            File photo = new File("src/test/resources/cat.png");
            app.contact().create(new ContactData().withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva")
                    .withAddress("St.Peterburg, Blan-Menilskaya str., 1, kv. 29").withMobilePhone("+79009009090").withEmail("email@domain.com").withPhoto(photo));
        }

    }
    @Test
    public void testContactModification() throws InterruptedException {
        app.goTo().homePage();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        File photo = new File("src/test/resources/cat.png");
        ContactData contact = new ContactData().withId(modifiedContact.getId())
                .withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva").withAddress("St.Peterburg, Blan-Menilskaya str., 1, kv. 29")
                .withMobilePhone("+79009009090").withHomePhone("88128121256").withWorkPhone("8800")
                .withEmail("email@domain.com").withEmail2("email2@domain.com").withEmail3("email3@domain.com").withPhoto(photo);
        app.contact().modify(contact);
        app.goTo().homePage();
        assertEquals(app.contact().count(), before.size());
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
