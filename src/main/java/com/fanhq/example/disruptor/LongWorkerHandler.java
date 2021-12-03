package com.fanhq.example.disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * @author fanhaiqiu
 * @date 2019/6/12
 */
public class LongWorkerHandler implements WorkHandler<LongEvent> {

    private String handlerName;

    public LongWorkerHandler(String name){
        this.handlerName = name;
    }

    @Override
    public void onEvent(LongEvent event) throws Exception {
        //TimeUnit.SECONDS.sleep(10);
        //System.out.println(this.handlerName);
        System.out.println("after:" + event.getValue());
    }
}
