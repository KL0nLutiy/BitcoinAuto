/**
 * Created by Vlad on 21.07.2016.
 */
public class Utils {

    public static void waitFor(long seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
