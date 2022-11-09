package ru.hogwarts.school35.exception;

public class FacultyNotFoundException extends RuntimeException {

    private long id;

    public FacultyNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

