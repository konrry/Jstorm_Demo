package net.galvin.jstorm.price;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.galvin.jstorm.utils.Logging;

public class PriceLaunch {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("applicationContext-lvmama-price-beans.xml");
        Logging.info("Hello World!");
        System.out.println("Hello World!");
    }

}
