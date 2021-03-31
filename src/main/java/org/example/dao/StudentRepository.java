package org.example.dao;

import org.example.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Student add(Student student);
    List<Student> getAll();
    Optional<Student> findById(int id);
    boolean removeById(int id);
    void update(Student student);
}
