package hu.infokristaly.backupservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ClipboardShareServiceApplication {

	public static void main(String[] args) {
		//SpringApplication.run(ClipboardShareServiceApplication.class, args);
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ClipboardShareServiceApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}

}
