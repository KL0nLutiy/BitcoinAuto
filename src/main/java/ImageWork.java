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

    public File saveScreenShot(WebElement element) {
        File screenshot = null;
        try {
            screenshot = ((TakesScreenshot) Main.driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = ImageIO.read(screenshot);

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

            BufferedImage eleScreenshot = fullImg.getSubimage(widthLoc, heightLoc, eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", screenshot);

        } catch (IOException e) {
            e.printStackTrace();
            Log.error("Cannot take screenshot", e);
        }

        return screenshot;
    }
}
