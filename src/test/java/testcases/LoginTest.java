package testcases;

import common.BaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
public class LoginTest extends BaseTest {
        LoginPage loginPage;

        @BeforeClass
        public void LoginTest(){
                loginPage = new LoginPage(driver);
        }

        @Test (priority = 1, description = "TC1_Dang nhap thanh cong")
        @Severity(SeverityLevel.CRITICAL)
        public void TC1_Login(){
                loginPage.getURL("https://the-internet.herokuapp.com/login");
                loginPage.enterUsername("tomsmith");
                loginPage.enterPassword("SuperSecretPassword!");
                loginPage.clickLogin();
                loginPage.verifyLogin("You logged into a secure area!", "1");
                loginPage.clickLogout();
        }

        @Test (priority = 2, description = "TC2_Dang nhap sai username")
        public void TC2_Login(){
                loginPage.enterUsername("tomsmith__");
                loginPage.enterPassword("SuperSecretPassword!");
                loginPage.clickLogin();
                loginPage.verifyLogin("Your username is invalid!___", "2");
        }
}
