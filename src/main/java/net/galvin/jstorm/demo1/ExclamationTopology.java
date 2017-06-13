package net.galvin.jstorm.demo1;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import net.galvin.jstorm.utils.Logging;

import java.util.Map;

/**
 * Created by galvin on 17-6-13.
 */
public class ExclamationTopology {


    public static class ExclamationBolt extends BaseRichBolt {

        OutputCollector _collector;

        @Override
        public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
            _collector = collector;
        }

        @Override
        public void execute(Tuple tuple) {
            Logging.info("ExclamationBolt.execute tuple: "+tuple.getString(0));
            _collector.emit(tuple, new Values(tuple.getString(0) + "!!!"));
            _collector.ack(tuple);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word"));
        }

    }

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word", new TestWordSpout(), 1);
        builder.setBolt("exclaim1", new ExclamationBolt(), 1).shuffleGrouping("word");
        builder.setBolt("exclaim2", new ExclamationBolt(), 1).shuffleGrouping("exclaim1");
        builder.setBolt("exclaim3", new ExclamationBolt(), 1).shuffleGrouping("exclaim2");
        builder.setBolt("exclaim4", new ExclamationBolt(), 1).shuffleGrouping("exclaim3");
        builder.setBolt("exclaim5", new ExclamationBolt(), 1).shuffleGrouping("exclaim4");
        builder.setBolt("exclaim6", new ExclamationBolt(), 1).shuffleGrouping("exclaim5");

        Config conf = new Config();
        conf.setDebug(true);
        conf.setNumWorkers(10);
        StormSubmitter.submitTopology("exclamationTopology", conf, builder.createTopology());
    }

}
