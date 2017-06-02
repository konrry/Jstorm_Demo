package net.galvin.jstorm.demo;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
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
        builder.setBolt("testbolt", new GalBlot(), 2).shuffleGrouping("testspout");

        Config conf = new Config();
        conf.setNumAckers(1);

        StormSubmitter.submitTopology("testtopology", conf, builder.createTopology());
        Logging.info("storm cluster will start");
    }


}
