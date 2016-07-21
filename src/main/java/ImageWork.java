import net.marketer.RuCaptcha;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Vlad on 20.07.2016.
 */
public class ImageWork {

    public static final Logger Log = Logger.getLogger(ImageWork.class);

    boolean isScrolled = false;

    public void saveScreenShot(WebElement element) {
        try {
            File screenshot = ((TakesScreenshot) Main.driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = ImageIO.read(screenshot);

            //Get the location of element on the page
            Point point = element.getLocation();
            //Get width and height of the element
            int eleWidth = element.getSize().getWidth();
            int eleHeight = element.getSize().getHeight();

            int eleLocationX = element.getLocation().getX();
            int eleLocationY = element.getLocation().getY();

            int widthLoc = 0;

            if (fullImg.getWidth() >= eleLocationX + eleWidth) {
                widthLoc = eleLocationX;
            }

            int heightLoc = 0;

            if (fullImg.getHeight() >= eleLocationY + eleHeight) {
                heightLoc = eleLocationY;
            } else {
                if (!isScrolled) {
                    WebElementUtils.moveToElement(element);
                    WebElementUtils.scrollUpRight(200);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.error("Can't sleep", e);
                    }
                    isScrolled = true;
                }
                screenshot = ((TakesScreenshot) Main.driver).getScreenshotAs(OutputType.FILE);
                fullImg = ImageIO.read(screenshot);
                heightLoc = fullImg.getHeight() - 200 - eleHeight;
            }

            //Crop the entire page screenshot to get only element screenshot
            BufferedImage eleScreenshot = fullImg.getSubimage(widthLoc, heightLoc, eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", screenshot);
            //Copy the element screenshot to disk
            /*File screenshotLocation = new File(".\\image\\screen.png");
            FileUtils.copyFile(screenshot, screenshotLocation);*/
            String response = RuCaptcha.postCaptcha(screenshot);
            String CAPCHA_ID;
            String decryption;
            if (response.startsWith("OK")) {
                CAPCHA_ID = response.substring(3);
                while (true){
                    response = RuCaptcha.getDecryption(CAPCHA_ID);
                    if(response.equals(RuCaptcha.Responses.CAPCHA_NOT_READY.toString())){
                        Thread.sleep(5000);
                    }else if(response.startsWith("OK")){
                        decryption = response.substring(3);
                        System.out.println(decryption);
                        break;
                    }else {
                        //обработка ошибок
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.error("Cannot take screenshot", e);
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Rucaptcha error", e);
        }
    }
}
