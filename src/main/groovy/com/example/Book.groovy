package com.example

import io.micronaut.serde.annotation.Serdeable

@Serdeable
class Book {
    Long id
    String title
    String author
}