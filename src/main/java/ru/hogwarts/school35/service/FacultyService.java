package ru.hogwarts.school35.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school35.exception.FacultyNotFoundException;
import ru.hogwarts.school35.model.Faculty;
import ru.hogwarts.school35.model.Student;
import ru.hogwarts.school35.repository.FacultyRepository;
import ru.hogwarts.school35.repository.StudentRepository;

import java.util.Collection;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty create(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);

    }

    public Faculty read(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
    }


    public Faculty update(long id, Faculty newFaculty) {
        Faculty oldFaculty = read(id);
        oldFaculty.setColor(newFaculty.getColor());
        oldFaculty.setName(newFaculty.getName());
        return facultyRepository.save(oldFaculty);
    }

    public Faculty delete(long id) {
        Faculty faculty = read(id);
        facultyRepository.delete(faculty);
        return faculty;

    }

    public Collection<Faculty> findByName(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public Collection<Student> getStudentOfFaculty(long faculty_id) {
        return studentRepository.findStudentByFaculty_Id(faculty_id);
    }
}





