package procamp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class SimpleTest {

    private WebDriver driver;

    @Before
    public void startBrowser()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @After
    public void stopBrowser()
    {
        driver.quit();
    }

    //Should it be via try/catch?
    private boolean isTagPresent (){
        return driver.findElements((By.tagName("h1"))).size() > 0;
    }

    //Is it better to collapse "get" methods into one with parameter? Should I wait for element here?
    private List<WebElement> getMenuItems(){
        return driver.findElements(By.cssSelector("li[id*='app-']"));
    }

    private List<WebElement> getSubMenuItems(){
        return driver.findElements(By.cssSelector("li[id*='doc-']"));
    }

    @Test
    public void test() {
        driver.get("http://demo.litecart.net/admin/");
        driver.findElement(By.cssSelector(".btn-default")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".name")));

        List<WebElement> menuItems = getMenuItems();
        List<WebElement> subMenuItems = getSubMenuItems();

        int menuItemsCount = menuItems.size();
        int subMenuItemsCount = subMenuItems.size();

        for (int i = 0; i < menuItemsCount; i++) {
            menuItems.get(i).click();
            Assert.assertTrue(isTagPresent());
            subMenuItems = getSubMenuItems();
            subMenuItemsCount = subMenuItems.size();

            if (subMenuItemsCount > 0){
                for (int j = 0; j < subMenuItemsCount; j++){
                    subMenuItems.get(j).click();
                    Assert.assertTrue(isTagPresent());
                    subMenuItems = getSubMenuItems();
                }
            }
            menuItems = getMenuItems();
        }
    }
}