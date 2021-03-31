package org.example;

import org.example.config.Config;
import org.example.dao.StudentRepository;
import org.example.entity.Student;
import org.example.utils.ConnectionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static void createStudentTable(ConnectionFactory connectionFactory) {
        var connection = connectionFactory.createConnection();

        try {
            Statement statement = connection.createStatement();

            statement.execute("create table if not exists Student (id int auto_increment, name varchar(200), gpa double)");
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) throws SQLException {

        var context = new AnnotationConfigApplicationContext(Config.class);

        ConnectionFactory connectionFactory = context.getBean(ConnectionFactory.class);

        createStudentTable(connectionFactory);

        var studentRepository = context.getBean(StudentRepository.class);

        Student jack = studentRepository.add(Student.createBuilder().name("Jack").gpa(2.0).build());
        studentRepository.add(Student.createBuilder().name("Casey").gpa(5.0).build());
        studentRepository.add(Student.createBuilder().name("Monica").gpa(4.0).build());

        for (Student student : studentRepository.getAll()) {
            System.out.println(student);
        }
        System.out.println();

        studentRepository.removeById(2);

        for (Student student : studentRepository.getAll()) {
            System.out.println(student);
        }
        System.out.println();

        jack.setGpa(4.0);

        studentRepository.update(jack);

        for (Student student : studentRepository.getAll()) {
            System.out.println(student);
        }

    }

}
