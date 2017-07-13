package net.galvin.jstorm.demo;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import net.galvin.jstorm.utils.Logging;
import net.galvin.jstorm.utils.Msg;

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
        Msg msg = (Msg) input.getValue(0);
        Fields fields = input.getFields();
        String field = fields.get(0);
        Logging.info("GalSubBlot.execute  msg: "+msg+", field: "+field);
        this.collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(this.field));
    }
}
