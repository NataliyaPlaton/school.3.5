package ru.hogwarts.school35.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school35.exception.StudentNotFoundException;
import ru.hogwarts.school35.model.Avatar;
import ru.hogwarts.school35.model.Faculty;
import ru.hogwarts.school35.model.Student;
import ru.hogwarts.school35.repository.AvatarRepository;
import ru.hogwarts.school35.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class StudentService {
    @Value("${path.to.avatars.folder}")

    private String avatarsDir;

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Student create(Student student) {
        student.setId(null);
        return studentRepository.save(student);

    }

    public Student read(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }


    public Student update(long id, Student newStudent) {
        Student oldStudent = read(id);
        oldStudent.setAge(newStudent.getAge());
        oldStudent.setName(newStudent.getName());
        return studentRepository.save(oldStudent);
    }


    public Student delete(long id) {
        Student student = read(id);
        studentRepository.delete(student);
        return student;
    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        if (min < 0 || max < 0 || max < min) {
            throw new IllegalArgumentException();
        }
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getNumberFacultyOfStudent(long student_id) {
        return studentRepository.findById(student_id).get().getFaculty();
    }

    public Avatar findAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        Student student = read(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        avatarRepository.save(avatar);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}


