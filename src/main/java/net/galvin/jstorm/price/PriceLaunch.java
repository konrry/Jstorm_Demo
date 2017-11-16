package net.galvin.jstorm.price;

import net.galvin.jstorm.price.strategy.jstorm.ITopologyLaunch;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PriceLaunch {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("applicationContext-lvmama-price-beans.xml");
        BeanFactory beanFactory = classPathXmlApplicationContext.getBeanFactory();
        ITopologyLaunch shipTopologyLaunch = (ITopologyLaunch) beanFactory.getBean("shipTopologyLaunch");
        shipTopologyLaunch.start();
    }

}
