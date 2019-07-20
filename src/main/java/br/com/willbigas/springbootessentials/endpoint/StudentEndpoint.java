package br.com.willbigas.springbootessentials.endpoint;


import br.com.willbigas.springbootessentials.error.ResourceNotFoundException;
import br.com.willbigas.springbootessentials.model.Student;
import br.com.willbigas.springbootessentials.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("students")
public class StudentEndpoint {

    private final StudentRepository studentDao;

    @Autowired
    public StudentEndpoint(StudentRepository studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(studentDao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        verifyIfStudentExists(id);
        Student student = studentDao.findById(id).get();
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping(path = "/findByName/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {
        return new ResponseEntity<>(studentDao.findStudentByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(studentDao.save(student), HttpStatus.CREATED);
    }

    @DeleteMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(@RequestBody Student student) {
        verifyIfStudentExists(student.getId());
        studentDao.delete(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfStudentExists(id);
        studentDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> update(@RequestBody Student student) {
        verifyIfStudentExists(student.getId());
        studentDao.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfStudentExists(Long id) {
        if (!studentDao.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Student not found for ID: " + id);
        }
    }


}
