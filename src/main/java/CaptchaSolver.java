import net.marketer.RuCaptcha;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by Vlad on 21.07.2016.
 */
public class CaptchaSolver {

    public static final Logger Log = Logger.getLogger(CaptchaSolver.class);

    private String CAPCHA_ID;

    public String simpleCaptchaPost(File screen) {
        String decryption="";
        try {
            String response = RuCaptcha.postCaptcha(screen);
            if (response.startsWith("OK")) {
                CAPCHA_ID = response.substring(3);
                while (true) {
                    response = RuCaptcha.getDecryption(CAPCHA_ID);
                    if (response.equals(RuCaptcha.Responses.CAPCHA_NOT_READY.toString())) {
                        Utils.waitFor(5);
                    } else if (response.startsWith("OK")) {
                        decryption = response.substring(3);
                        System.out.println(decryption);
                        break;
                    } else {
                        if(response.contains("ERROR_WRONG_USER_KEY")) {
                            Log.error("Check api key, must be 32 characters.");
                        } else if(response.contains("ERROR_KEY_DOES_NOT_EXIST")) {
                            Log.error("Check api key, it does not exist.");
                        } else if (response.contains("ERROR_ZERO_BALANCE")) {
                            Log.error("Check your balance");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Cannot post captcha",e);
        }

        return decryption;
    }

    public void badCaptchaReport(){
        try {
            String badCaptchaReportStatus = RuCaptcha.reportBad(CAPCHA_ID);
            if(badCaptchaReportStatus.contains("OK")) {
                Log.info("Report bad captcha SUCCESS.");
            } else if(badCaptchaReportStatus.contains("REPORT_NOT_RECORDED")) {
                Log.error("Cannot report bad captcha. See more about REPORT_NOT_RECORDED.");
            } else if(badCaptchaReportStatus.contains("ERROR_WRONG_CAPTCHA_ID")) {
                Log.error("Cannot report bad captcha. See more about ERROR_WRONG_CAPTCHA_ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Report bad captcha error",e);
        }
    }

}
