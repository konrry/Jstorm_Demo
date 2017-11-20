package net.galvin.jstorm.price;

import net.galvin.jstorm.price.strategy.jstorm.ITopologyLaunch;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PriceLaunch {

    private final static String[] TOPOLOGY_LAUNCH_ARR = new String[]{
            "shipTopologyLaunch", "visaTopologyLaunch"
    };

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("applicationContext-lvmama-price-beans.xml");
        BeanFactory beanFactory = classPathXmlApplicationContext.getBeanFactory();
        for (String topologyLaunch : TOPOLOGY_LAUNCH_ARR) {
            ITopologyLaunch shipTopologyLaunch = (ITopologyLaunch) beanFactory.getBean(topologyLaunch);
            shipTopologyLaunch.start();
        }
    }

}
