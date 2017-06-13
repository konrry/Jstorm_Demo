package net.galvin.jstorm.utils;

/**
 * Created by galvin on 17-6-13.
 */
public class Utils {

    public static void sleep(long milliSeconds){
        try {
            Thread.sleep(milliSeconds);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
