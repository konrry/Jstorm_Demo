package net.galvin.jstorm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/6/2.
 */
public class Logging {

    private final static Logger LOGGER = LoggerFactory.getLogger(Logging.class);

    public static void info(String info){
        LOGGER.info(info);
    }

    public static void error(String info){
        LOGGER.error(info);
    }

}
