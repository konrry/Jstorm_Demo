package net.galvin.jstorm.strategy;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import net.galvin.jstorm.utils.Logging;
import net.galvin.jstorm.utils.Msg;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/2.
 */
public class StrategyBlot extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        ComCalData comCalData = (ComCalData) input.getValue(0);
        Fields fields = input.getFields();
        String field = fields.get(0);
        Logging.info("GalBlot.execute  comCalData: "+comCalData+", field: "+field);
        this.collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    }

}
