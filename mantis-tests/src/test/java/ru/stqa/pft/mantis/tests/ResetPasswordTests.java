package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ResetPasswordTests extends TestBase{
    @BeforeMethod
    public void startMailServer(){
        app.mail().start();
    }

    @Test
    public void TestResetPassword() throws MessagingException, IOException {
        long now = System.currentTimeMillis();
        String password = String.format("password%s", now);
        app.login().authorization();
        app.navigation().goToUsers();
        User user = app.db().getUsersFromDb().iterator().next();
        String passwordBefore = user.getPasswordEncrypted();
        app.reset().resetPassword(user.getUsername());
        MailMessage mess = app.mail().waitForMailForUser(1, 1000, user.getEmail());
        String confirmationLink = findConfirmationLink(mess);
        app.registration().finish(confirmationLink, password);
        User userAfter = app.db().getUsersFromDb().stream().filter((m) -> m.getId() == user.getId()).findFirst().get();
        String passwordAfter = userAfter.getPasswordEncrypted();


        assertTrue(app.newSession().login(user.getUsername(), password));
        assertFalse(passwordBefore.equals(passwordAfter));
    }

    private String findConfirmationLink(MailMessage mess) {
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mess.text);
    }

    @AfterMethod
    public void stopMailServer(){
        app.mail().stop();
    }

}
