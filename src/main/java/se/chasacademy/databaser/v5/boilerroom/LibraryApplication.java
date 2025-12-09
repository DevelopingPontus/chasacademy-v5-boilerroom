package se.chasacademy.databaser.v5.boilerroom;

import java.util.Locale.Category;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import se.chasacademy.databaser.v5.boilerroom.Repositories.AuthorRepository;
import se.chasacademy.databaser.v5.boilerroom.Repositories.BookRepository;
import se.chasacademy.databaser.v5.boilerroom.Repositories.CategoryRepository;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {
	private BookRepository bookRepository;
	private AuthorRepository authorRepository;
	private CategoryRepository categoryRepository;

	public LibraryApplication(BookRepository bookRepository, AuthorRepository authorRepository,
			CategoryRepository categoryRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
		this.categoryRepository = categoryRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Välkommen till Bibliotek Z");

		System.out.println("Alla böcker:");
		// bookRepository.findAll().forEach(System.out::println);

		System.out.println("Kategorier:");
		categoryRepository.findAll().forEach(category -> System.out.println(category.getName()));
		System.out.println("---------------------");
		System.out.println("Författare:");
		// authorRepository.findAll().forEach(System.out::println);
		System.out.println("---------------------");

		int borrowedBooks = bookRepository.getBooksBorrowed();
		System.out.println("Antal utlånade böcker: " + borrowedBooks);
		int notBorrowedBooks = bookRepository.getBooksNotBorrowedAllLibraries();
		System.out.println("Antal ej utlånade böcker: " + notBorrowedBooks);

		int activeMembers = bookRepository.getMembersWhoBorrowedBooks();
		System.out.println("Medlemmar som lånat böcker: " + activeMembers);

		var top10 = bookRepository.getTop10BooksAllLibraries();
		System.out.println("Topp 10 populära böcker på alla bibliotek:");
		top10.forEach(entry -> {
			System.out.println("Boktitel: " + entry.title() + ", Antal lån: " + entry.loanCount());
		});

		{
			var top10PerLibrary = bookRepository.getTop10BooksPerLibrary();
			System.out
					.println("Topp 10 populära böcker per bibliotek:");
			top10PerLibrary.forEach(entry -> {
				System.out.println("Bibliotek: " + entry.libraryName() +
						", Boktitel: " + entry.data().title() +
						", Antal lån: " + entry.data().loanCount());
			});
		}
		{
			var booksPerCategoryAllLibraries = bookRepository.getBooksPerCategoryAllLibraries();
			System.out
					.println("Antalet böcker per kategori för alla bibliotek:");
			booksPerCategoryAllLibraries.forEach(entry -> {
				System.out.println("Kategori: " + entry.categoryName() +
						", Antal böcker: " + entry.bookCount());
			});
		}

		{
			var booksPerCategoryPerLibrary = bookRepository.getBooksPerCategoryPerLibrary();
			System.out
					.println("Antalet böcker per kategori för varje bibliotek:");
			booksPerCategoryPerLibrary.forEach(entry -> {
				System.out.println("Bibliotek: " + entry.libraryName() +
						", Kategori: " + entry.data().categoryName() +
						", Antal böcker: " + entry.data().bookCount());
			});
		}
	}
}
