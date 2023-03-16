package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoTest extends TestBase {
    @BeforeMethod
    private void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.goTo().groupPage();
            if (app.group().all().size() == 0 || !app.group().isThereAGroupName("test2")) {
                app.group().create(new GroupData().withName("test2"));
            }
            app.contact().create(new ContactData().withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva")
                    .withAddress("St.Peterburg").withMobilePhone("+79009009090").withEmail("email@domain.com").withGroup("test2"));
        }
        app.goTo().homePage();
    }

    @Test
    public void testContactInfo() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat((contact.getLastname()+"\n"+contact.getFirstname()+"\n"+contact.getAddress()),
                equalTo(mergeInfo(contactInfoFromEditForm)));
    }

    private String mergeInfo(ContactData contact) {
        return Arrays.asList(contact.getLastname(), contact.getFirstname(), contact.getAddress())
                .stream().filter((s) -> ! s.equals(""))
                .map(ContactInfoTest::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String info) {
        return info.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}


