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
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;


import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class AddContactToGroupTests extends TestBase{
ContactData contactWithGroup;
    @BeforeMethod
    public void ensurePreconditions(){
        if (app.db().contacts().size() == 0){
            File photo = new File("src/test/resources/cat.png");
            app.contact().create(new ContactData().withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva")
                    .withAddress("St.Peterburg, Blan-Menilskaya str., 1, kv. 29").withMobilePhone("+79009009090").withEmail("email@domain.com").withPhoto(photo));
            app.goTo().homePage();
        }
        contactWithGroup = app.db().contacts().iterator().next();
        if(contactWithGroup.getGroups().size() >= app.db().groups().size() || app.db().groups().size() == 0){
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test2"));
        }
    }
    @Test
    public void testAddContactToGroup(){
        app.goTo().homePage();
        Groups contactGroupsBefore = contactWithGroup.getGroups();
        int idGroup = app.contact().addInGroup(contactWithGroup, contactGroupsBefore);
        GroupData groupToAdd = app.db().group(idGroup);
        ContactData cont = app.db().contact(contactWithGroup.getId());
        Groups contactGroupsAfter = cont.getGroups();
        assertThat(contactGroupsAfter.size(), equalTo(contactGroupsBefore.size() + 1));
        assertThat(contactGroupsAfter, equalTo(contactGroupsBefore.withAdded(groupToAdd)));

    }
}
