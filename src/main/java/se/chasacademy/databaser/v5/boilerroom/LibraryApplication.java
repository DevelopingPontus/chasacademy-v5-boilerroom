package se.chasacademy.databaser.v5.boilerroom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.chasacademy.databaser.v5.boilerroom.Repositories.BookRepository;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {
	private BookRepository bookRepository;

	public LibraryApplication(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Välkommen till Bibliotek Z");

		bookRepository.findAll().forEach(System.out::println);
	}
}
