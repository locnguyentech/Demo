package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import supports.WebUI;

public class LoginPage {
    private WebDriver driver;
    private WebUI webUI;

    public LoginPage(WebDriver _driver){
        driver = _driver;
        webUI = new WebUI(driver);
    }

    //===============================================================================================
    By usernameTxt = By.id("username");
    By passwordTxt = By.id("password");
    By loginBtn = By.xpath("//button[@type='submit']");
    By verifyLoginLbl = By.xpath("//div[@id='flash']");
    By logoutBtn = By.xpath("//a[@class='button secondary radius']");

    //===============================================================================================
    public void getURL(String url){
        driver.get(url);
        webUI.waitForPageLoaded();
    }
    public void enterUsername(String username){
        webUI.setText(usernameTxt, username);
    }
    public void enterPassword(String password){
        webUI.setText(passwordTxt, password);
    }
    public void clickLogin(){
        webUI.clickElement(loginBtn);
    }
    public void clickLogout(){
        webUI.clickElement(logoutBtn);
    }

    public void verifyLogin(String msg, String option){
        switch (option){
            case "1": //happy case
                Assert.assertEquals(webUI.getElement(verifyLoginLbl).getText().trim().substring(0,30), msg.trim());
                break;
            case "2": //invalid username
                Assert.assertEquals(webUI.getElement(verifyLoginLbl).getText().substring(0,25), msg.trim());
                break;
            case "3": //invalid password
                Assert.assertEquals(webUI.getElement(verifyLoginLbl).getText().substring(0,30), msg.trim());
                break;
            default: //blank
                Assert.assertEquals(webUI.getElement(verifyLoginLbl).getText().substring(0,30), msg.trim());
                break;
        }
    }
}
