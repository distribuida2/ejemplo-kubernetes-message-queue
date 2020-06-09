package ar.edu.undav.app.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("worker")
@Component
public class RabbitStoreListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitStoreListener.class);

    @Autowired
    private Queue itemsToProcessQueue;

    @RabbitListener(queues = "#{itemsToProcessQueue.name}", concurrency = "1-1")
    public void onMessage(String message) {
        try {
            LOGGER.info("Processing task " + message);
            Thread.sleep(5000);
            LOGGER.info("Completed task " + message);
            ElasticStoreRabbitQueueService.COUNTER.incrementAndGet();
        } catch (InterruptedException e) {
            LOGGER.warn("Error procesando el item");
        }
    }
}

