package demo2prep.ConsRESTfulWS;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class ConsResTfulWsApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	void contextLoads() {
		assertThat(restTemplate).isNotNull();
	}

}
