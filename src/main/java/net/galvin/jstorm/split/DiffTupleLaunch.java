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
 * 不同的tuple发送给多个blot
 */
public class DiffTupleLaunch {


    public static void main(String[] args) {
        DiffTopology iTopology = new DiffTopology();
        iTopology.start("DiffTopology");

    }

    private static class DiffTopology implements ITopology{

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
            builder.setSpout("DiffSpout", new DiffSpout(), 1);
            builder.setBolt("DiffBlot", new DiffBlot(), 1).shuffleGrouping("DiffSpout");
            builder.setBolt("DiffSubBlotA", new DiffSubBlot(), 1).shuffleGrouping("DiffBlot", STREAM_ID.ONE.name());
            builder.setBolt("DiffSubBlotB", new DiffSubBlot(), 1).shuffleGrouping("DiffBlot", STREAM_ID.TWO.name());
            builder.setBolt("DiffSubBlotC", new DiffSubBlot(), 1).shuffleGrouping("DiffBlot", STREAM_ID.THREE.name());

            Config config = new Config();
            config.setNumAckers(3);
            config.setNumWorkers(5);
            StormTopology stormTopology = builder.createTopology();
            StormSubmitter.submitTopology(topologyName, config, stormTopology);
            Logging.info("Jstorm cluster will start");

        }

    }

    private static class DiffSpout extends BaseRichSpout {

        private SpoutOutputCollector collector;

        @Override
        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void nextTuple() {
            Msg msg = Msg.Builder.get();
            Logging.info("DiffSpout.nextTuple: "+msg);
            this.collector.emit(new Values(msg),msg.getId());
            Utils.sleep(10000l);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("MSG"));
        }

        @Override
        public void ack(Object msgId) {
            Logging.info("DiffSpout.ack: "+msgId);
        }

        @Override
        public void fail(Object msgId) {
            Logging.info("DiffSpout.fail: "+msgId);
        }
    }


    private static class DiffBlot extends BaseRichBolt {

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
            Logging.info("DiffBlot.execute  msg: "+msg+", field: "+field);

            String data = (String) msg.getData();
            String[] dataArr = data.split("=");

            this.collector.emit(STREAM_ID.ONE.name(), new Values(new Msg(dataArr[0])));
            this.collector.emit(STREAM_ID.TWO.name(), new Values(new Msg(dataArr[1])));
            this.collector.emit(STREAM_ID.THREE.name(), new Values(new Msg(dataArr[2])));
            this.collector.ack(input);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declareStream(STREAM_ID.ONE.name(), new Fields("MSG", "DIFF_ONE"));
            declarer.declareStream(STREAM_ID.TWO.name(), new Fields("MSG", "DIFF_TWO"));
            declarer.declareStream(STREAM_ID.THREE.name(), new Fields("MSG", "DIFF_THREE"));
        }
    }

    private static class DiffSubBlot extends BaseRichBolt {

        private OutputCollector collector;

        @Override
        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void execute(Tuple input) {
            Msg msg = (Msg) input.getValue(0);
            String streamId = input.getSourceStreamId();
            Fields fields = input.getFields();
            String field = fields.get(0);
            Logging.info("DiffSubBlot.execute streamId: "+streamId+"  msg: "+msg+", field: "+field);
            this.collector.ack(input);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("SAME_SUB_DEMO"));
        }
    }


    private enum STREAM_ID {
        ONE,TWO,THREE;
    }

}
