import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Vlad on 20.07.2016.
 */
public class WebElementUtils {

    public static WebElement initElementWhenLoaded(By by){
        WebDriver driver = Main.driver;
        WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(Configuration.getInstance().getProperty(Configuration.ELEMENT_TIMEOUT))*1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by);
    }

    public static boolean isElementExist(By by) {
        return Main.driver.findElements(by).size() > 0;
    }

    public static void moveToElement(WebElement element){
        Actions actions = new Actions(Main.driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public static void scrollUpRight(int pixels){
        JavascriptExecutor js = (JavascriptExecutor ) Main.driver;
        js.executeScript("window.scrollBy(0," + pixels + ")", "");
    }
}

