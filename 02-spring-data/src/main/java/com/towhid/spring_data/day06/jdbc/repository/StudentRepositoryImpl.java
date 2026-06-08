package com.towhid.spring_data.day06.jdbc.repository;

import com.towhid.spring_data.day06.jdbc.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

// @Repository = @Component but for data layer
// Spring knows this is a database class
@Repository
public class StudentRepositoryImpl implements StudentRepository {

    // JdbcTemplate is Spring's main JDBC helper
    // Spring auto-creates it using our DataSource config
    private final JdbcTemplate jdbcTemplate;

    // Constructor injection — best practice
    public StudentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ─────────────────────────────────────────
    // ROW MAPPER
    // ─────────────────────────────────────────
    // RowMapper tells Spring HOW to convert
    // one DB row → one Java object
    // ResultSet = one row from DB result
    private final RowMapper<Student> studentRowMapper = new RowMapper<Student>() {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();

            student.setId(rs.getInt("id"));
            // rs.getInt("id") → reads 'id' column as int

            student.setName(rs.getString("name"));
            // rs.getString("name") → reads 'name' column as String

            student.setEmail(rs.getString("email"));

            student.setAge(rs.getInt("age"));

            student.setCourse(rs.getString("course"));

            student.setGrade(rs.getDouble("grade"));

            return student;
            // return the fully built Student object
        }
    };
    // Spring calls mapRow() for EACH row returned

    // ─────────────────────────────────────────
    // SAVE (INSERT)
    // ─────────────────────────────────────────
    @Override
    public Student save(Student student) {

        String sql = "INSERT INTO students (name, email, age, course, grade) " +
                "VALUES (?, ?, ?, ?, ?)";
        // ? = placeholder — prevents SQL injection!
        // Never put values directly in SQL string

        // KeyHolder captures the auto-generated ID
        // from MySQL after insert
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            // Create PreparedStatement with the SQL
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
                    // tells MySQL: give back the generated ID
            );

            // Fill in the ? placeholders in order
            ps.setString(1, student.getName());   // ? #1 = name
            ps.setString(2, student.getEmail());   // ? #2 = email
            ps.setInt(3, student.getAge());        // ? #3 = age
            ps.setString(4, student.getCourse());  // ? #4 = course
            ps.setDouble(5, student.getGrade());   // ? #5 = grade

            return ps;
        }, keyHolder);

        // Get the generated ID and set it on student
        student.setId(keyHolder.getKey().intValue());
        // keyHolder.getKey() = the ID MySQL generated

        return student;
        // return student now WITH the id set
    }

    // ─────────────────────────────────────────
    // FIND BY ID (SELECT WHERE id = ?)
    // ─────────────────────────────────────────
    @Override
    public Optional<Student> findById(Integer id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try {
            // queryForObject = expects exactly ONE result
            Student student = jdbcTemplate.queryForObject(
                    sql,
                    studentRowMapper,
                    // studentRowMapper converts row → Student
                    id
                    // fills the ? with id value
            );
            return Optional.of(student);
            // wrap in Optional = "I found it!"

        } catch (Exception e) {
            // queryForObject throws exception if 0 results
            return Optional.empty();
            // Optional.empty() = "nothing found"
        }
    }

    // ─────────────────────────────────────────
    // FIND ALL (SELECT *)
    // ─────────────────────────────────────────
    @Override
    public List<Student> findAll() {
        String sql = "SELECT * FROM students ORDER BY id";

        // query() = returns a List
        // calls studentRowMapper for EACH row
        return jdbcTemplate.query(sql, studentRowMapper);
    }

    // ─────────────────────────────────────────
    // FIND BY COURSE
    // ─────────────────────────────────────────
    @Override
    public List<Student> findByCourse(String course) {
        String sql = "SELECT * FROM students WHERE course = ?";

        // query() with parameter
        return jdbcTemplate.query(sql, studentRowMapper, course);
        // course fills the ?
    }

    // ─────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────
    @Override
    public boolean update(Student student) {
        String sql = "UPDATE students SET name=?, email=?, " +
                "age=?, course=?, grade=? WHERE id=?";

        // update() returns number of rows affected
        int rowsAffected = jdbcTemplate.update(
                sql,
                student.getName(),    // ? #1
                student.getEmail(),   // ? #2
                student.getAge(),     // ? #3
                student.getCourse(),  // ? #4
                student.getGrade(),   // ? #5
                student.getId()       // ? #6 - WHERE id = ?
        );

        return rowsAffected > 0;
        // true = at least 1 row was updated
        // false = no rows updated (id not found)
    }

    // ─────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────
    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM students WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, id);
        // update() is used for INSERT, UPDATE, DELETE
        // any SQL that CHANGES data

        return rowsAffected > 0;
    }

    // ─────────────────────────────────────────
    // COUNT
    // ─────────────────────────────────────────
    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM students";

        // queryForObject with a simple type
        // no RowMapper needed for single values
        return jdbcTemplate.queryForObject(sql, Integer.class);
        // Integer.class = return type we expect
    }


    public List<Student> findByGradeGreaterThan(Double grade){
        String sql = "SELECT * FROM students WHERE grade > ?";
        return jdbcTemplate.query(sql,studentRowMapper,grade);
    }

    public Student updateGrade(Integer id,Double newGrade){
       String sql = "SELECT * FROM students WHERE id = ? ";
       Student student = jdbcTemplate.queryForObject(sql,studentRowMapper,id);
       student.setGrade(newGrade);

       jdbcTemplate.update(
               "UPDATE students SET grade = ? WHERE id = ?",
                     newGrade,//               student.getGrade(),
                     id           //               student.getId()
       );
       return student;
    }
}