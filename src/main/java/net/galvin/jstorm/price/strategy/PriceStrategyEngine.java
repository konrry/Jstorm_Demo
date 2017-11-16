package net.galvin.jstorm.price.strategy;

import net.galvin.jstorm.price.pojo.ComCalData;

public interface PriceStrategyEngine {

    /**
     * 定价策略计算入口
     */
    void calculate(ComCalData comCalData) throws Exception;

}
