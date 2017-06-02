package net.galvin.jstorm.demo;

/**
 * Created by Administrator on 2017/6/2.
 */
public class GalLaunch {

    public static void main(String[] args) {
        ITopology iTopology = new GalTopology();
        iTopology.start();
    }

}
