package net.galvin.jstorm.price.strategy.jstorm;

import net.galvin.jstorm.utils.Logging;
import org.springframework.stereotype.Service;

@Service("visaTopologyLaunch")
public class VisaTopologyLaunch implements ITopologyLaunch {

    public void start() {
        Logging.info(" VisaTopologyLaunch start ");
        Logging.info(" VisaTopologyLaunch end ");
    }

}
