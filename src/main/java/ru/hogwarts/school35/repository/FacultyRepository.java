package ru.hogwarts.school35.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school35.model.Faculty;

import java.util.Collection;


@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByNameIgnoreCase(String name);

    Collection<Faculty> findByColorIgnoreCase(String color);
}
