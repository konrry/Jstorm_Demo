package net.galvin.jstorm.demo;

import backtype.storm.topology.TopologyBuilder;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/2.
 */
public class GalTopology implements ITopology {

    @Override
    public void start(){
        System.out.println("This is GalTopology.start()");
        TopologyBuilder builder = new TopologyBuilder();
        System.out.println(CollectionUtils.isEmpty(new ArrayList()));;
    }

}
