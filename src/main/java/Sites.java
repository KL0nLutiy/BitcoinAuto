import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
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
        File screen = imageWork.saveScreenShot(captcha);

        if(screen==null) {
            return;
        }

        CaptchaSolver captchaSolver = new CaptchaSolver();

        while (true) {
            String decription = captchaSolver.simpleCaptchaPost(screen);
            WebElement captchaInput = WebElementUtils.initElementWhenLoaded(By.id("vercode"));
            captchaInput.sendKeys(decription);

            WebElement submit = formInputs.get(0);
            submit.click();

            if(WebElementUtils.isElementExist(By.className("alert"))) {
                WebElement alertText = WebElementUtils.initElementWhenLoaded(By.className("alert"));

                if(alertText.getText().contains("Invalid")) {
                    captchaSolver.badCaptchaReport();
                } else {
                    break;
                }
            }
        }

    }
}
