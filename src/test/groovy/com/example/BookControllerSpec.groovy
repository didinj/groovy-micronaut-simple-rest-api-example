package com.example

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class BookControllerSpec extends Specification {

    @Inject
    EmbeddedServer embeddedServer

    @Inject
    HttpClient httpClient

    def "should list all books"() {
        when:
        def response = httpClient.toBlocking().exchange(HttpRequest.GET("/books"), List)

        then:
        response.status == HttpStatus.OK
        response.body().size() >= 2
    }

    def "should retrieve a book by id"() {
        when:
        def response = httpClient.toBlocking().exchange(HttpRequest.GET("/books/1"), Book)

        then:
        response.status == HttpStatus.OK
        response.body().title == "Groovy in Action"
    }

    def "should add a new book"() {
        given:
        def newBook = new Book(title: "Testing with Spock", author: "Jane Doe")

        when:
        def response = httpClient.toBlocking().exchange(
            HttpRequest.POST("/books", newBook),
            Book
        )

        then:
        response.status == HttpStatus.OK
        response.body().title == "Testing with Spock"
    }

    def "should update a book"() {
        given:
        def updatedBook = new Book(title: "Micronaut Updated", author: "John Doe")

        when:
        def response = httpClient.toBlocking().exchange(
            HttpRequest.PUT("/books/2", updatedBook),
            Book
        )

        then:
        response.status == HttpStatus.OK
        response.body().title == "Micronaut Updated"
    }

    def "should delete a book"() {
        when:
        def response = httpClient.toBlocking().exchange(HttpRequest.DELETE("/books/1"))

        then:
        response.status == HttpStatus.NO_CONTENT || response.status == HttpStatus.OK
    }
}