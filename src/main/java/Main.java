import net.marketer.RuCaptcha;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Vlad on 20.07.2016.
 */
public class Main {

    static WebDriver driver;

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", Configuration.getInstance().getProperty(Configuration.DRIVER_PATH));

        driver = new ChromeDriver();

        RuCaptcha.API_KEY = Configuration.getInstance().getProperty(Configuration.API_KEY);

        Sites sites = new Sites();
        sites.coinSteller();

    }
}
