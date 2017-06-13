package net.galvin.jstorm.demo;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.TupleImpl;
import backtype.storm.tuple.Values;
import net.galvin.jstorm.utils.Logging;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/2.
 */
public class GalBlot extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        Long temp = input.getLong(0);
        Fields fields = input.getFields();
        String field = fields.get(0);
        Logging.info("GalBlot.execute  temp: "+temp+", field: "+field);
        collector.emit(new Values(temp));
        this.collector.emit(String.valueOf(temp % 3),new Values(temp));
        this.collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("GAL_DEMO"));
        declarer.declareStream("0",new Fields("GAL_DEMO_0"));
        declarer.declareStream("1",new Fields("GAL_DEMO_1"));
        declarer.declareStream("2",new Fields("GAL_DEMO_2"));
    }
}
