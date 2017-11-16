package net.galvin.jstorm.price.strategy.jstorm;

import net.galvin.jstorm.utils.Logging;
import org.springframework.stereotype.Service;

@Service("shipTopologyLaunch")
public class ShipTopologyLaunch implements ITopologyLaunch {

    public void start() {
        Logging.info(" ShipTopologyLaunch start ");
        Logging.info(" ShipTopologyLaunch end ");
    }

}
