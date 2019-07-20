package br.com.willbigas.springbootessentials.endpoint;


import br.com.willbigas.springbootessentials.error.CustomErrorType;
import br.com.willbigas.springbootessentials.model.Student;
import br.com.willbigas.springbootessentials.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
public class StudentEndpoint {

    private final StudentRepository studentDao;

    @Autowired
    public StudentEndpoint(StudentRepository studentDao) {
        this.studentDao = studentDao;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(studentDao.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        Student student = studentDao.findById(id).get();
        if (student == null)
            return new ResponseEntity<>(new CustomErrorType(" Student not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student) {
        return new ResponseEntity<>(studentDao.save(student), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Student student) {
        studentDao.delete(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        studentDao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        studentDao.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
