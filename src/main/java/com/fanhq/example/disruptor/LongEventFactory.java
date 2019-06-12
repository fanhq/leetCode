package com.fanhq.example.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author fanhaiqiu
 * @date 2019/6/12
 */
public class LongEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
