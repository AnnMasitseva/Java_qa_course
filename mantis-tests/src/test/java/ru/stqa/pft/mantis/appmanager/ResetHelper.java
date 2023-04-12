package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class ResetHelper extends HelperBase{
    public ResetHelper(ApplicationManager app) {
        super(app);
    }
    public void resetPassword(String username) {
        click(By.linkText(username));
        click(By.xpath("//input[@value='Reset Password']"));
    }
}
