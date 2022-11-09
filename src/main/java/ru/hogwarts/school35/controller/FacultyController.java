package ru.hogwarts.school35.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school35.model.Faculty;
import ru.hogwarts.school35.model.Student;
import ru.hogwarts.school35.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> read(@PathVariable long id) {
        Faculty faculty = facultyService.read(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> findByNameOrColor(@RequestParam(required = false) String name,
                                                                 @RequestParam(required = false) String color) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(facultyService.findByName(name));
        }
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByColor(color));
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> update(@PathVariable long id,
                                          @RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.update(id, faculty);
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> delete(@PathVariable long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("studentOfFaculty")
    public ResponseEntity<Collection<Student>> getStudentOfFaculty(@RequestParam long faculty_id) {
        return ResponseEntity.ok(facultyService.getStudentOfFaculty(faculty_id));
    }

}

