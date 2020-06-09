package ar.edu.undav.app.service;

import ar.edu.undav.app.SpringBootApplicationTests;
import ar.edu.undav.app.queue.ElasticStoreRabbitQueueService;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;

@Ignore
public class QueueServiceServiceImplTest extends SpringBootApplicationTests {

	@Autowired
	private ElasticStoreRabbitQueueService queueService;
}
