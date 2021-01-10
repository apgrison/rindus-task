package apgrison.codingtask;

import apgrison.codingtask.controller.RestTemplateResponseErrorHandler;
import apgrison.codingtask.model.FileLocator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {

		final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate restTemplate = restTemplateBuilder
				.errorHandler(new RestTemplateResponseErrorHandler())
				.build();
		restTemplate.setRequestFactory(requestFactory);

		return restTemplate;
	}

	@Bean
	public FileLocator fileLocator() {
		return new FileLocator();
	}

	@Bean
	public HttpHeaders httpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.set("Content-Type", "application/json");
		return headers;
	}

}
