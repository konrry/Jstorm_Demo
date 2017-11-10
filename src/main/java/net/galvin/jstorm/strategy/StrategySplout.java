package net.galvin.jstorm.strategy;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import net.galvin.jstorm.utils.Logging;
import net.galvin.jstorm.utils.Msg;

import java.util.Map;

/**
 *   spout对象必须是继承Serializable， 因此要求spout内所有数据结构必须是可序列化的
     spout可以有构造函数，但构造函数只执行一次，是在提交任务时，创建spout对象，因此在task分配到具体worker之前的初始化工作可以在此处完成，
 一旦完成，初始化的内容将携带到每一个task内（因为提交任务时将spout序列化到文件中去，在worker起来时再将spout从文件中反序列化出来）。
     open是当task起来后执行的初始化动作
     close是当task被shutdown后执行的动作
     activate 是当task被激活时，触发的动作
     deactivate 是task被deactive时，触发的动作
     nextTuple 是spout实现核心， nextuple完成自己的逻辑，即每一次取消息后，用collector 将消息emit出去。
     strategy， 当spout收到一条ack消息时，触发的动作，详情可以参考 ack机制
     fail， 当spout收到一条fail消息时，触发的动作，详情可以参考 ack机制
     declareOutputFields， 定义spout发送数据，每个字段的含义
     getComponentConfiguration 获取本spout的component 配置
 */
public class StrategySplout extends BaseRichSpout {

    private SpoutOutputCollector collector;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        Logging.info("StrategySplout.open");
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        ComCalData comCalData = new ComCalData(1,1, ComCalData.OBJECT_TYPE.PROD_PRODUCT);
        Logging.info("StrategySplout.nextTuple: "+comCalData);
        this.collector.emit(new Values(comCalData),comCalData.getId());
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("comCalData"));
    }

    @Override
    public void ack(Object msgId) {
        Logging.info("StrategySplout.strategy: "+msgId);
    }

    @Override
    public void fail(Object msgId) {
        Logging.info("StrategySplout.fail: "+msgId);
    }
}
