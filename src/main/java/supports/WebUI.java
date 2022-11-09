package supports;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WebUI {
    WebDriver driver;
    WebDriverWait wait;
    Select select;
    Actions action;
    JavascriptExecutor js;

    int PAGE_LOAD_TIMEOUT = 19;
    int ELEMENT_LOAD_TIMEOUT = 19;

    public WebUI(WebDriver _driver) {
        driver = _driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(ELEMENT_LOAD_TIMEOUT));
        action = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
            wait.until(jQueryLoad);
            wait.until(jsLoad);
        } catch (Throwable error) {
            error.getMessage();
        }
    }

    public void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement getElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        sleep(1);
        return driver.findElement(locator);
    }

    public List<WebElement> getElements(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElements(locator);
    }

    public boolean verifyElementExist(By locator) {
        List<WebElement> listElement = driver.findElements(locator);
        if (listElement.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void moveToElement(By locator) {
        action.moveToElement(getElement(locator)).perform();
    }

    public void scrollToElement(By locator) {
        js.executeScript("arguments[0].scrollIntoView(true);", getElement(locator));
    }

    public void pressENTER(By locator) {
        action.moveToElement(getElement(locator)).sendKeys(Keys.ENTER).perform();
    }

    public void pressENTER() {
        action.sendKeys(Keys.ENTER).perform();
    }

    public void selectOptionByText(By locator, String Text) {
        select = new Select(getElement(locator));
        select.selectByVisibleText(Text);
    }

    public void selectOptionByValue(By locator, String Value) {
        select = new Select(getElement(locator));
        select.selectByValue(Value);
    }

    public void selectOptionByIndex(By locator, int Index) {
        select = new Select(getElement(locator));
        select.selectByIndex(Index);
    }

    public String getElementText(By locator) {
        return getElement(locator).getText();
    }

    public String getElementText(WebElement webElement) {
        return webElement.getText();
    }

    public boolean verifyElementText(By locator, String text) {
        return getElement(locator).getText().equals(text);
    }

    public void clickElement(By element) {
        getElement(element).click();
    }

    public void setText(By element, String value) {
        getElement(element).sendKeys(value);
    }

    public void logConsole(Object value){
        System.out.println(value);
    }

    public void switchToFrame(String frameName){
        driver.switchTo().frame(frameName);
    }

    public void switchToDefaultFrame(){
        driver.switchTo().defaultContent();
    }

}
