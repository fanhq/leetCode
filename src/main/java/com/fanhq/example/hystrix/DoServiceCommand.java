package com.fanhq.example.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * @author fanhaiqiu
 * @date 2019/3/14
 */
public class DoServiceCommand extends HystrixCommand<String> {


    public DoServiceCommand() {
        super(setter());
    }

    @Override
    protected String run() throws Exception {
        //处理业务
        return null;
    }


    private static Setter setter() {
        //服务组
        HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("do-service");
        //命令配置
        HystrixCommandProperties.Setter commmadProperties = HystrixCommandProperties.Setter()
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                .withFallbackEnabled(true)
                .withFallbackIsolationSemaphoreMaxConcurrentRequests(100)
                .withExecutionIsolationThreadInterruptOnFutureCancel(true)
                .withExecutionTimeoutEnabled(true)
                .withExecutionTimeoutInMilliseconds(1000);
        return HystrixCommand.Setter.withGroupKey(groupKey)
                .andCommandPropertiesDefaults(commmadProperties);
    }

    @Override
    protected String getFallback(){
        return "降级返回";
    }
}
