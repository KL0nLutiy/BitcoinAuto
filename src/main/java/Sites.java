import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Vlad on 20.07.2016.
 */
public class Sites {

    public void coinSteller(){
        Main.driver.get("http://coinsteller.com/");

        List<WebElement> formInputs = WebElementUtils.initElementWhenLoaded(By.tagName("form")).findElements(By.tagName("input"));

        WebElement address = formInputs.get(3);
        address.sendKeys(Configuration.getInstance().getProperty(Configuration.BITCOIN_ADRESS));

        WebElement captcha = WebElementUtils.initElementWhenLoaded(By.id("pixel"));
        ImageWork imageWork = new ImageWork();
        imageWork.saveScreenShot(captcha);



    }
}
