package br.com.gguife.screenmatch;

import br.com.gguife.screenmatch.controller.Client;
import br.com.gguife.screenmatch.repository.SeriesRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}
	@Autowired
	private SeriesRepository repository;

	@Override
	public void run(String... args) throws Exception {
		Client client = new Client(repository);
		client.showMenu();
	}
}
