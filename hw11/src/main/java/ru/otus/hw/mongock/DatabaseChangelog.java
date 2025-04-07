package ru.otus.hw.mongock;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    private static final String TEREKHIN = "Terekhin";

    private final List<Author> authors = new ArrayList<>();

    private final List<Genre> genres = new ArrayList<>();

    private final List<Book> books = new ArrayList<>();

    @ChangeSet(author = TEREKHIN, id = "dropDb", order = "01", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(author = TEREKHIN, id = "insertAuthors", order = "02")
    public void insertAuthors(AuthorRepository repository) {
        authors.add(Author.builder().id("1").fullName("Author_1").build());
        authors.add(Author.builder().id("2").fullName("Author_2").build());
        authors.add(Author.builder().id("3").fullName("Author_3").build());
        authors.forEach(author -> repository.save(author).block());
    }

    @ChangeSet(author = TEREKHIN, id = "insertGenres", order = "03")
    public void insertGenres(GenreRepository repository) {
        genres.add(Genre.builder().id("1").name("Genre_1").build());
        genres.add(Genre.builder().id("2").name("Genre_2").build());
        genres.add(Genre.builder().id("3").name("Genre_3").build());
        genres.forEach(genre -> repository.save(genre).block());
    }

    @ChangeSet(author = TEREKHIN, id = "insertBooks", order = "04")
    public void insertBooks(BookRepository repository) {
        books.add(Book.builder().id("1").title("BookTitle_1").author(authors.get(0)).genre(genres.get(0)).build());
        books.add(Book.builder().id("2").title("BookTitle_2").author(authors.get(1)).genre(genres.get(1)).build());
        books.add(Book.builder().id("3").title("BookTitle_3").author(authors.get(2)).genre(genres.get(2)).build());
        books.forEach(book -> repository.save(book).block());
    }

    @ChangeSet(author = TEREKHIN, id = "insertComments", order = "05")
    public void insertComments(CommentRepository repository) {
        List<Comment> comments = new ArrayList<>();
        comments.add(Comment.builder().id("1").text("Text_1").book(books.get(0)).build());
        comments.add(Comment.builder().id("2").text("Text_2").book(books.get(1)).build());
        comments.add(Comment.builder().id("3").text("Text_3").book(books.get(2)).build());
        comments.forEach(comment -> repository.save(comment).block());
    }

}