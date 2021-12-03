package com.fanhq.example.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author fanhaiqiu
 * @date 2019/6/12
 */
public class LongEventMain {
    public static void main(String[] args) throws InterruptedException {

        // The factory for the event
        LongEventFactory eventFactory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 4;

        // Construct the Disruptor
        DefaultThreadFactory defaultThreadFactory = new DefaultThreadFactory();
        WaitStrategy waitStrategy = new BlockingWaitStrategy();
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory, bufferSize, defaultThreadFactory, ProducerType.MULTI, waitStrategy);

        // Connect the handler
        // disruptor.handleEventsWith(new LongEventHandler());
        LongWorkerHandler handler1 = new LongWorkerHandler("handler1");
        LongWorkerHandler handler2 = new LongWorkerHandler("handler2");
        disruptor.handleEventsWithWorkerPool(handler1, handler2);

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; l<24; l++) {
            TimeUnit.SECONDS.sleep(1);
            bb.putLong(0, l);
            producer.onData(bb);
        }
    }
}
