package net.galvin.jstorm.strategy;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import net.galvin.jstorm.ITopology;
import net.galvin.jstorm.utils.Logging;

public class StrategyTopology implements ITopology {

    @Override
    public void start(String topologyName){
        try {
            this.doStart(topologyName);
        } catch (Exception e) {
            Logging.error(e);
        }
    }

    private void doStart(String topologyName) throws AlreadyAliveException, InvalidTopologyException {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("strategySpout", new StrategySplout(), 1);
        builder.setBolt("strategyBlot", new StrategyBlot(), 1).shuffleGrouping("strategySpout");

        Config config = new Config();
        config.setNumAckers(2);
        config.setNumWorkers(5);
        StormTopology stormTopology = builder.createTopology();
        StormSubmitter.submitTopology(topologyName, config, stormTopology);
        Logging.info("jstorm cluster will start");
    }

}
