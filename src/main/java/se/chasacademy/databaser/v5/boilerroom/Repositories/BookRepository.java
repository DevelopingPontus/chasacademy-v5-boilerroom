package se.chasacademy.databaser.v5.boilerroom.Repositories;

import java.util.List;

import se.chasacademy.databaser.v5.boilerroom.Models.Author;
import se.chasacademy.databaser.v5.boilerroom.Models.Book;
import se.chasacademy.databaser.v5.boilerroom.RowMappers.BookRowMapper;
import se.chasacademy.databaser.v5.boilerroom.Models.BookPopularity;
import se.chasacademy.databaser.v5.boilerroom.Models.LibraryStat;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    private JdbcClient jdbcClient;
    private AuthorRepository authorRepository;

    public record CategoryStat(String categoryName, int bookCount) {
    }

    public BookRepository(JdbcClient jdbcClient, AuthorRepository authorRepository) {
        this.jdbcClient = jdbcClient;
        this.authorRepository = authorRepository;
    }

    public List<Book> findAll() {
        List<Book> books = jdbcClient
                .sql("""
                        select b.isbn, b.name as title, b.publication_date, c.category_id, c.name as category from books b
                                join category c on b.category_id = c.category_id
                        """)
                .query(new BookRowMapper())
                .list();
        for (Book book : books) {
            List<Author> authors = authorRepository.findByBook(book.getIsbn());
            book.setAuthors(authors);
        }
        return books;
    }

    // * Antalet böcker utlånade respektive ej utlånade på de olika biblioteken.
    // --antalet böcker ej utlånade på de olika biblioteken.
    // select lib.name as bibliotek,
    // count(*) as antal_ej_utlanade
    // from library lib
    // join book_copy c on c.library_id = lib.library_id
    // left join loans l
    // on l.copy_id = c.copy_id
    // and current_date between l.start_date and l.end_date
    // where l.copy_id is null
    // group by lib.name;
    /*
     * public List<Tuple<String, Integer>> getBooksNotBorrowed() {
     * return jdbcClient.sql("""
     * select lib.name as bibliotek,
     * count(*) as antal_ej_utlanade
     * from library lib
     * join book_copy c on c.library_id = lib.library_id
     * left join loans l
     * on l.copy_id = c.copy_id
     * and current_date between l.start_date and l.end_date
     * where l.copy_id is null
     * group by lib.name
     * """)
     * .query(new StringIntegerTupleRowMapper())
     * .list();
     * }
     */

    // * Antalet böcker utlånade för alla bibliotek.
    // --antalet böcker utlånade för alla bibliotek.
    // select count(*) as antal_utlånade
    // from loans
    // where current_date between start_date and end_date;
    public int getBooksBorrowed() {
        return jdbcClient.sql("""
                select count(*) as antal_utlånade
                from loans
                where current_date between start_date and end_date
                """)
                .query(Integer.class)
                .single();
    }

    // * Antalet böcker ej utlånade för alla bibliotek.
    // --antalet böcker ej utlånade för alla bibliotek.
    // select count(*) as antal_ej_utlånade
    // from book_copy
    // where copy_id not in (
    // select copy_id from loans
    // where current_date between start_date and end_date
    // );
    public int getBooksNotBorrowedAllLibraries() {
        return jdbcClient.sql("""
                select count(*) as antal_ej_utlånade
                from book_copy
                where copy_id not in (
                select copy_id from loans
                where current_date between start_date and end_date
                )
                """)
                .query(Integer.class)
                .single();
    }

    // * Top 10 lista på populär böcker per bibliotek.
    // --top 10 lista på populär böcker per bibliotek.
    // select lib.name as bibliotek, b.name as boktitel, count(*) as antal_lan
    // from library lib
    // join book_copy c on c.library_id = lib.library_id
    // join loans l on l.copy_id = c.copy_id
    // join books b on c.isbn = b.isbn
    // group by lib.name, b.name
    // order by lib.name, antal_lan desc
    // limit 10;
    public List<LibraryStat<BookPopularity>> getTop10BooksPerLibrary() {
        return jdbcClient.sql("""
                select lib.name as bibliotek, b.isbn, b.name as boktitel, count(*) as antal_lan
                from library lib
                join book_copy c on c.library_id = lib.library_id
                join loans l on l.copy_id = c.copy_id
                join books b on c.isbn = b.isbn
                group by lib.name, b.isbn, b.name
                order by lib.name, antal_lan desc
                limit 10
                """)
                .query((rs, rowNum) -> new LibraryStat<BookPopularity>(
                        rs.getString("bibliotek"),
                        new BookPopularity(
                                rs.getString("isbn"),
                                rs.getString("boktitel"),
                                rs.getInt("antal_lan"))))
                .list();
    }

    // * Top 10 lista på populär böcker för alla bibliotek.
    // --top 10 lista på populär böcker för alla bibliotek.
    // select b.isbn, b.name, count(*) as antal_lån
    // from loans l
    // join book_copy c on l.copy_id = c.copy_id
    // join books b on c.isbn = b.isbn
    // group by b.isbn, b.name
    // order by antal_lan desc
    // limit 10;
    public List<BookPopularity> getTop10BooksAllLibraries() {
        return jdbcClient.sql("""
                select b.isbn, b.name as boktitel, count(*) as antal_lan
                from loans l
                join book_copy c on l.copy_id = c.copy_id
                join books b on c.isbn = b.isbn
                group by b.isbn, b.name
                order by antal_lan desc
                limit 10
                """)
                .query((rs, rowNum) -> new BookPopularity(
                        rs.getString("isbn"),
                        rs.getString("boktitel"),
                        rs.getInt("antal_lan")))
                .list();
    }

    // * Se hur många medborgare som lånat en eller flera böcker.
    // --se hur många medborgare som lånat en eller flera böcker.
    // select count(distinct member_id) as antal_medlemmar_som_lånat
    // from loans;
    public int getMembersWhoBorrowedBooks() {
        return jdbcClient.sql("""
                select count(distinct member_id) as antal_medlemmar_som_lånat
                from loans
                """)
                .query(Integer.class)
                .single();
    }

    // * Se hur många böcker som aldrig blivit utlånade.
    // --se hur många böcker som aldrig blivit utlånade.
    // select count(*) as böcker_aldrig_utlånade
    // from books
    // where isbn not in (
    // select distinct isbn
    // from book_copy
    // where copy_id in (select copy_id from loans)
    // );
    public int getBooksNeverBorrowed() {
        return jdbcClient.sql("""
                select count(*) as böcker_aldrig_utlånade
                from books
                where isbn not in (
                select distinct isbn
                from book_copy
                where copy_id in (select copy_id from loans)
                )
                """)
                .query(Integer.class)
                .single();
    }

    // * Se antalet böcker per kategori för alla bibliotek.
    // --se antalet böcker per kategori för alla bibliotek.
    // select c.name, count(*) from category c left join books b on c.category_id =
    // b.category_id group by c.category_id;
    public List<CategoryStat> getBooksPerCategoryAllLibraries() {
        return jdbcClient.sql("""
                select c.name, count(*) from category c
                left join books b on c.category_id = b.category_id
                group by c.category_id
                """)
                .query((rs, rowNum) -> new CategoryStat(
                        rs.getString("name"),
                        rs.getInt("count")))
                .list();
    }

    // * Se antalet böcker per kategori för varje bibliotek.
    // --se antalet böcker per kategori för varje bibliotek.
    // select
    // lib.name as bibliotek,
    // cat.name as kategori,
    // count(distinct b.isbn) as antal_bocker
    // from library lib
    // join book_copy bc on bc.library_id = lib.library_id
    // join books b on b.isbn = bc.isbn
    // join book_author ba on ba.isbn = b.isbn
    // join category cat on cat.category_id = b.category_id
    // group by lib.name, cat.name
    // order by lib.name, cat.name;
    public List<LibraryStat<CategoryStat>> getBooksPerCategoryPerLibrary() {
        return jdbcClient.sql("""
                select lib.name as bibliotek,
                cat.name as kategori,
                count(distinct b.isbn) as antal_bocker
                from library lib
                join book_copy bc on bc.library_id = lib.library_id
                join books b on b.isbn = bc.isbn
                join book_author ba on ba.isbn = b.isbn
                join category cat on cat.category_id = b.category_id
                group by lib.name, cat.name
                order by lib.name, cat.name
                """)
                .query((rs, rowNum) -> new LibraryStat<>(
                        rs.getString("bibliotek"),
                        new CategoryStat(
                                rs.getString("kategori"),
                                rs.getInt("antal_bocker"))))
                .list();
    }

}