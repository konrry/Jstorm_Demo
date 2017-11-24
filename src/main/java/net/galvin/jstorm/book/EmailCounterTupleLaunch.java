package net.galvin.jstorm.book;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import net.galvin.jstorm.ITopology;
import net.galvin.jstorm.utils.Logging;
import net.galvin.jstorm.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class EmailCounterTupleLaunch {


    public static void main(String[] args) {
        EmailCounterTTopology iTopology = new EmailCounterTTopology();
        iTopology.start("EmailCounterTTopology");
    }

    private static class EmailCounterTTopology implements ITopology{

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
            builder.setSpout("CommitFeedSpout", new CommitFeedSpout());
            builder.setBolt("EmailExtractorBlot", new EmailExtractorBlot(), 1).shuffleGrouping("CommitFeedSpout");
            builder.setBolt("EmailCounterBlot", new EmailCounterBlot())
                    .fieldsGrouping("EmailExtractorBlot", new Fields("email"));

            Config config = new Config();
            StormTopology stormTopology = builder.createTopology();
            StormSubmitter.submitTopology(topologyName, config, stormTopology);
            Logging.info("Jstorm cluster will start");

        }

    }

    private static class CommitFeedSpout extends BaseRichSpout {

        private SpoutOutputCollector collector;

        private String[] emailArr = {
                                      "1 chuqiang@163.com", "2 chuqiang@163.com", "3 chuqiang@163.com",
                                      "4 yandongquan@163.com", "5 yandongquan@163.com", "6 yandongquan@163.com",
                                      "7 yandongquan@163.com", "8 yandongquan@163.com", "9 yandongquan@163.com",
                                      "10 zhangwei@163.com", "11 zhangwei@163.com", "12 zhangwei@163.com",
                                      "13 zhangwei@163.com", "14 zhangwei@163.com", "15 zhangwei@163.com",
                                      "16 zhangwei@163.com", "17 zhangwei@163.com", "18 zhangwei@163.com",
                                      "19 chenzhihua@163.com", "20 chenzhihua@163.com", "21 chenzhihua@163.com",
                                      "22 chenzhihua@163.com", "23 chumingqiang@163.com", "24 chumingqiang@163.com",
                                      "25 chumingqiang@163.com", "26 chumingqiang@163.com", "27 chumingqiang@163.com"
                                    };

        @Override
        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void nextTuple() {
            for (String tempStr : emailArr) {
                Logging.info("BookSpout.nextTuple: "+ tempStr);
                this.collector.emit(new Values(tempStr));
            }
            Utils.sleep(30000l);
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("commit"));
        }

        @Override
        public void ack(Object msgId) {}

        @Override
        public void fail(Object msgId) {}
    }

    private static class EmailExtractorBlot extends BaseBasicBolt {

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("email"));
        }

        @Override
        public void execute(Tuple input, BasicOutputCollector collector) {
            String commit = input.getStringByField("commit");
            String[] pairs = commit.split(" ");
            collector.emit(new Values(pairs[1]));
        }
    }

    private static class EmailCounterBlot extends BaseBasicBolt {

        private Map<String, Integer> counts;

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {}

        @Override
        public void prepare(Map stormConf, TopologyContext context) {
            counts = new HashMap<String, Integer>();
        }

        @Override
        public void execute(Tuple input, BasicOutputCollector collector) {
            String email = input.getStringByField("email");
            counts.put(email, countFor(email) + 1);
            printCounts();
        }

        private Integer countFor(String email){
            Integer count = counts.get(email);
            return count == null ? 0 : count;
        }

        private void printCounts(){
            for (String email : counts.keySet()){
                System.out.println(String.format("%s has count of $s", email, counts.get(email)));
            }
        }

    }

}
