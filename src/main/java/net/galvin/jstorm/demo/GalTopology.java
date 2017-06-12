package net.galvin.jstorm.demo;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import net.galvin.jstorm.demo.utils.Logging;

/**
 * Created by Administrator on 2017/6/2.
 */
public class GalTopology implements ITopology {

    @Override
    public void start(){
        try {
            this.doStart();
        } catch (Exception e) {
            Logging.error(e.getMessage());
        }
    }


    private void doStart() throws AlreadyAliveException, InvalidTopologyException {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("testspout", new GalSplout(), 1);
        builder.setBolt("testboltA", new GalBlot(), 1).shuffleGrouping("testspout");
        builder.setBolt("testboltB", new GalBlot(), 1).shuffleGrouping("testspout");

        Config configonf = new Config();
        configonf.setNumAckers(1);
        configonf.setNumWorkers(10);
        StormTopology stormTopology = builder.createTopology();
        StormSubmitter.submitTopology("testtopology", configonf, stormTopology);
        Logging.info("storm cluster will start");
    }


}
