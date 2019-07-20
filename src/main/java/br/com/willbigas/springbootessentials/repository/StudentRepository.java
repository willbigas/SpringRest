package br.com.willbigas.springbootessentials.repository;

import br.com.willbigas.springbootessentials.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    List<Student> findStudentByNameIgnoreCaseContaining(String name);
}
