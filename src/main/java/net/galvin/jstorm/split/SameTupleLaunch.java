package net.galvin.jstorm.split;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import net.galvin.jstorm.ITopology;
import net.galvin.jstorm.utils.Logging;
import net.galvin.jstorm.utils.Msg;
import net.galvin.jstorm.utils.Utils;

import java.util.Map;

/**
 * 相同的tuple发送给多个blot
 */
public class SameTupleLaunch {


    public static void main(String[] args) {
        SameTopology iTopology = new SameTopology();
        iTopology.start("SameTopology");

    }

    private static class SameTopology implements ITopology{

        @Override
        public void start(String topologyName) {
            try {
                this.doStart(topologyName);
            } catch (Exception e) {
                Logging.error(e);
            }
        }


        private void doStart(String topologyName) throws AlreadyAliveException, InvalidTopologyException {

            TopologyBuilder builder = new TopologyBuilder();
            builder.setSpout("SameSpout", new SameSpout(), 1);
            builder.setBolt("SameBlot", new SameBlot(), 1).shuffleGrouping("SameSpout");
            builder.setBolt("SameSubBlotA", new SameSubBlot(), 1).shuffleGrouping("SameBlot");
            builder.setBolt("SameSubBlotB", new SameSubBlot(), 1).shuffleGrouping("SameBlot");
            builder.setBolt("SameSubBlotC", new SameSubBlot(), 1).shuffleGrouping("SameBlot");

            Config config = new Config();
            config.setNumAckers(3);
            config.setNumWorkers(5);
            StormTopology stormTopology = builder.createTopology();
            StormSubmitter.submitTopology(topologyName, config, stormTopology);
            Logging.info("Jstorm cluster will start");

        }

    }

    private static class SameSpout extends BaseRichSpout {

        private SpoutOutputCollector collector;

        @Override
        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void nextTuple() {
            Msg msg = Msg.Builder.get();
            Logging.info("SameSpout.nextTuple: "+msg);
            this.collector.emit(new Values(msg),msg.getId());
            Utils.sleep(10000l);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("MSG"));
        }

        @Override
        public void ack(Object msgId) {
            Logging.info("SameSpout.ack: "+msgId);
        }

        @Override
        public void fail(Object msgId) {
            Logging.info("SameSpout.fail: "+msgId);
        }
    }


    private static class SameBlot extends BaseRichBolt {

        private OutputCollector collector;

        @Override
        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void execute(Tuple input) {
            Msg msg = (Msg) input.getValue(0);
            Fields fields = input.getFields();
            String field = fields.get(0);
            Logging.info("SameBlot.execute  msg: "+msg+", field: "+field);
            this.collector.emit(new Values(msg));
            this.collector.ack(input);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("SAME_DEMO"));
        }
    }

    private static class SameSubBlot extends BaseRichBolt {

        private OutputCollector collector;

        @Override
        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void execute(Tuple input) {
            Msg msg = (Msg) input.getValue(0);
            Fields fields = input.getFields();
            String field = fields.get(0);
            Logging.info("SameSubBlot.execute  msg: "+msg+", field: "+field);
            this.collector.ack(input);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("SAME_SUB_DEMO"));
        }
    }

}
