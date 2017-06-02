package net.galvin.jstorm.demo;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import net.galvin.jstorm.demo.utils.Logging;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/2.
 */
public class GalBlot extends BaseRichBolt implements Serializable {

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        Logging.info("GalBlot.prepare");
    }

    @Override
    public void execute(Tuple input) {
        String temp = input.getString(0);
        Logging.info("GalBlot.execute: "+temp);
        this.collector.ack(input);
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            Logging.error(e.getMessage());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }

}
