package br.com.bgrbarbosa.ms_exame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsExameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsExameApplication.class, args);
	}

}
