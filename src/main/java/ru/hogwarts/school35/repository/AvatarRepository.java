package ru.hogwarts.school35.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school35.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByStudentId(Long student_id);
}
