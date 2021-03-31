package org.example.dao;

import org.example.entity.Student;
import org.example.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private static final String QUERY_ALL_STUDENT =
           "select id as student_id, name as student_name, gpa as student_gpa from Student";

    private static final String QUERY_STUDENT_BY_ID =
            "select id as student_id, name as student_name, gpa as student_gpa from Student where id = ?";

    private static final String DELETE_STUDENT_BY_ID =
            "delete from Student where id = ?";

    private static final String UPDATE_STUDENT_BY_ID =
            "update Student set name = ?, gpa = ? where id = ?";

    private static final String INSERT_STUDENT  =
            "insert into Student (name, gpa) values (?, ?)";

    private final ConnectionFactory connectionFactory;

    public StudentRepositoryImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Student add(Student student) {
        var connection = connectionFactory.createConnection();

        try (var statement =
                     connection.prepareStatement(INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, student.getName());
            statement.setDouble(2, student.getGpa());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException();
            }

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                student.setId(generatedKeys.getInt(1));
            }
            else {
                throw new RuntimeException();
            }

            return student;
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Student> getAll() {
        var connection = connectionFactory.createConnection();

        try (var statement = connection.createStatement()) {

            var resultSet = statement.executeQuery(QUERY_ALL_STUDENT);

            final List<Student> students = new ArrayList<>();

            while (resultSet.next()) {
                students.add(mapResultSetToEntity(resultSet));
            }

            return students;
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Student> findById(int id) {
        var connection = connectionFactory.createConnection();

        try (var statement = connection.prepareStatement(QUERY_STUDENT_BY_ID)) {

            statement.setInt(1, id);

            var resultSet = statement.executeQuery();

            return resultSet.next() ?
                        Optional.of(mapResultSetToEntity(resultSet)) :
                        Optional.empty();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean removeById(int id) {
        var connection = connectionFactory.createConnection();

        try (var statement = connection.prepareStatement(DELETE_STUDENT_BY_ID)) {

            statement.setInt(1, id);

            int affectedRowsNum = statement.executeUpdate();

            return affectedRowsNum > 0;
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Student student) {
        var connection = connectionFactory.createConnection();

        try (var statement = connection.prepareStatement(UPDATE_STUDENT_BY_ID)) {

            statement.setString(1, student.getName());
            statement.setDouble(2, student.getGpa());
            statement.setInt(3, student.getId());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Student mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        var student = new Student();

        student.setId(resultSet.getInt("student_id"));
        student.setName(resultSet.getString("student_name"));
        student.setGpa(resultSet.getDouble("student_gpa"));

        return student;
    }

}
