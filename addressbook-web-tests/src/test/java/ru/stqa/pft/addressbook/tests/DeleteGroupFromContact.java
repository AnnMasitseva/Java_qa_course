package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteGroupFromContact extends TestBase {
    ContactData contactToDeleteFromGroup;
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            File photo = new File("src/test/resources/cat.png");
            app.contact().create(new ContactData().withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva")
                    .withAddress("St.Peterburg, Blan-Menilskaya str., 1, kv. 29").withMobilePhone("+79009009090").withEmail("email@domain.com").withPhoto(photo));
            app.goTo().homePage();
        }
        contactToDeleteFromGroup = app.db().contacts().iterator().next();
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test2"));
            app.goTo().homePage();
            app.contact().addIntoGroup(contactToDeleteFromGroup);
            contactToDeleteFromGroup = app.db().contact(contactToDeleteFromGroup.getId());
        }

        if (contactToDeleteFromGroup.getGroups().size() == 0 && app.db().groups().size() != 0) {
            app.goTo().homePage();
            app.contact().addIntoGroup(contactToDeleteFromGroup);
            contactToDeleteFromGroup = app.db().contact(contactToDeleteFromGroup.getId());
        }
    }
    @Test
    public void testDeletionContactFromGroup() {
        app.goTo().homePage();
        int groupIdToDeleteFrom = contactToDeleteFromGroup.getGroups().iterator().next().getId();
        Groups contactGroupsBefore = contactToDeleteFromGroup.getGroups();
        app.contact().deleteFromGroup(contactToDeleteFromGroup, groupIdToDeleteFrom);
        GroupData groupToDelete = app.db().group(groupIdToDeleteFrom);
        ContactData cont = app.db().contact(contactToDeleteFromGroup.getId());
        Groups contactGroupsAfter = cont.getGroups();
        assertThat(contactGroupsAfter.size(), equalTo(contactGroupsBefore.size() - 1));
        assertThat(contactGroupsAfter, equalTo(contactGroupsBefore.without(groupToDelete)));
    }
}
