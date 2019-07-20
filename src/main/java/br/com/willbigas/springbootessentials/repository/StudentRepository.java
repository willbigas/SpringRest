package br.com.willbigas.springbootessentials.repository;

import br.com.willbigas.springbootessentials.model.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findStudentByName(String name);
}
