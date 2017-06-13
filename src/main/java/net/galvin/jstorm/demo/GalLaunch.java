package net.galvin.jstorm.demo;

import net.galvin.jstorm.utils.Logging;

/**
 * Created by Administrator on 2017/6/2.
 */
public class GalLaunch {

    public static void main(String[] args) {
//        String path = args[0];
//        Logging.info("path:"+path);
        ITopology iTopology = new GalTopology();
        iTopology.start();
    }

}
