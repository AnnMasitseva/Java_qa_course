package ru.stqa.pft.addressbook.tests;

import net.bytebuddy.implementation.bytecode.Throw;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDelitionTests extends TestBase{
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
                    .withAddress("St.Peterburg").withMobilePhone("+79009009090").withEmail("email@domain.com").withPhoto(photo));
        }
    }
    @Test
    public void testContactDelition() throws InterruptedException {
        app.goTo().homePage();
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().homePage();
        assertEquals(app.contact().count(), before.size()-1);
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(deletedContact)));
    }


}
