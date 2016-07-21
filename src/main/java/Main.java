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





        /*driver.get("http://www.google.com");

        WebElement element = driver.findElement(By.name("q"));

        // Вводим ключевое слово для поиска
        element.sendKeys("гладиолус");

        // Отправляем форму в которой находится элемент element.
        // WebDriver сам найдет, в какой он форме.
        element.submit();

        // Выводим в консоль заголовок страницы
        System.out.println("Page title is: " + driver.getTitle());*/
    }
}
