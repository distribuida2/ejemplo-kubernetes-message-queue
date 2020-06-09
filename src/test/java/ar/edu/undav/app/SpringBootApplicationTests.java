package ar.edu.undav.app;

import ar.edu.undav.app.queue.ElasticStoreRabbitQueueService;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class SpringBootApplicationTests {

    private static final String QUEUE_NAME = "testQueue";

    @Rule
	public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker() {
		@Override
		protected void configure() {
			try {
				this.getBrokerService().addConnector("tcp://localhost:61616");

			} catch (Exception e) {
				// noop test should fail
			}
		}
	};

	@Autowired
	private ElasticStoreRabbitQueueService queueService;

	@Before
    public void setup() {
    }

	@Test
	public void testSend() throws Exception {
		queueService.send(QUEUE_NAME, "test");
		assertThat(queueService.pendingJobs(QUEUE_NAME)).isEqualTo(1);
	}
}
