package com.fanhq.example.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author fanhaiqiu
 * @date 2019/6/12
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event.getValue());
    }

}
