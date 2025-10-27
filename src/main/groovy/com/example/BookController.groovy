package com.example

import io.micronaut.http.annotation.*

@Controller("/books")
class BookController {

    private List<Book> books = [
        new Book(id: 1, title: "Groovy in Action", author: "Dierk König"),
        new Book(id: 2, title: "Learning Micronaut", author: "John Doe")
    ]

    @Get("/")
    List<Book> list() {
        books
    }

    @Get("/{id}")
    Book show(Long id) {
        books.find { it.id == id }
    }

    @Post("/")
    Book add(@Body Book book) {
        book.id = (books*.id.max() ?: 0) + 1
        books << book
        book
    }

    @Put("/{id}")
    Book update(Long id, @Body Book updatedBook) {
        def index = books.findIndexOf { it.id == id }
        if (index != -1) {
            books[index].title = updatedBook.title
            books[index].author = updatedBook.author
            return books[index]
        }
        null
    }

    @Delete("/{id}")
    void delete(Long id) {
        books.removeIf { it.id == id }
    }
}