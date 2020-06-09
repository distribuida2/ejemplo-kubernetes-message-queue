package ar.edu.undav.app.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ElasticStoreRabbitQueueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticStoreRabbitQueueService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    static AtomicInteger COUNTER = new AtomicInteger();

    public int completedJobs() {
        return COUNTER.get();
    }

    public void send(String destination, String message) {
        LOGGER.info("sending message='{}' to destination='{}'", message, destination);
        rabbitTemplate.convertAndSend(destination, message);
    }

    public int pendingJobs(String queueName) {
        return rabbitAdmin.getQueueInfo(queueName).getMessageCount();
    }

    public boolean isUp() {
        ConnectionFactory connection = rabbitTemplate.getConnectionFactory();
        try {
            connection.createConnection().close();
            return true;
        } catch (AmqpConnectException e) {
            LOGGER.info("MessageBroker offline", e);
        }
        return false;
    }


}
