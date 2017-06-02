package net.galvin.jstorm.demo;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import net.galvin.jstorm.demo.utils.Logging;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *   spout对象必须是继承Serializable， 因此要求spout内所有数据结构必须是可序列化的
     spout可以有构造函数，但构造函数只执行一次，是在提交任务时，创建spout对象，因此在task分配到具体worker之前的初始化工作可以在此处完成，
 一旦完成，初始化的内容将携带到每一个task内（因为提交任务时将spout序列化到文件中去，在worker起来时再将spout从文件中反序列化出来）。
     open是当task起来后执行的初始化动作
     close是当task被shutdown后执行的动作
     activate 是当task被激活时，触发的动作
     deactivate 是task被deactive时，触发的动作
     nextTuple 是spout实现核心， nextuple完成自己的逻辑，即每一次取消息后，用collector 将消息emit出去。
     ack， 当spout收到一条ack消息时，触发的动作，详情可以参考 ack机制
     fail， 当spout收到一条fail消息时，触发的动作，详情可以参考 ack机制
     declareOutputFields， 定义spout发送数据，每个字段的含义
     getComponentConfiguration 获取本spout的component 配置
 */
public class GalSplout extends BaseRichSpout implements Serializable {

    private static final long serialVersionUID = 1576248436296314425L;

    private SpoutOutputCollector collector;
    AtomicLong atomicLong = new AtomicLong(0L);

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        Logging.info("GalSplout.open");
        this.collector = collector;
    }

    @Override
    public void nextTuple() {
        for(int count=0;count < 10;count++){
            Long a = atomicLong.incrementAndGet();
            Logging.info("GalSplout.nextTuple: "+a);
            this.collector.emit(new Values(a));
            try {
                Thread.sleep(10000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("GAL_DEMO"));
    }

    @Override
    public void ack(Object msgId) {
        super.ack(msgId);
    }

    @Override
    public void fail(Object msgId) {
        super.fail(msgId);
    }
}
