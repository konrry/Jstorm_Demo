package net.galvin.jstorm.strategy;

import net.galvin.jstorm.ITopology;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2017/6/2.
 */
public class StrategyLaunch {

    public static void main(String[] args) {
        String topologyName = "strategyTopology";
        if(args != null && args.length > 0 && StringUtils.isNotEmpty(args[0])){
            topologyName = args[0];
        }
        ITopology iTopology = new StrategyTopology();
        iTopology.start(topologyName);
    }

}
