package net.galvin.jstorm.demo;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import net.galvin.jstorm.utils.Logging;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/2.
 */
public class GalSubBlot extends BaseRichBolt {

    private OutputCollector collector;

    private String field;

    public GalSubBlot(String field) {
        this.field = field;
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        Long temp = input.getLong(0);
        Logging.info("GalSubBlot.execute: "+temp);
//        collector.emit(new Values(temp));
//        this.collector.emit(String.valueOf(temp % 3),new Values(temp));
        this.collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(this.field));
    }
}
