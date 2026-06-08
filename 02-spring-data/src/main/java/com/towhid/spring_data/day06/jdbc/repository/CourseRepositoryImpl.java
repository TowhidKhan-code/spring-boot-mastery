package com.towhid.spring_data.day06.jdbc.repository;

import com.towhid.spring_data.day06.jdbc.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepositoryImpl implements CourseRepository{

    private final JdbcTemplate jdbcTemplate;

    public CourseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Course> courseRowMapper = new RowMapper<Course>() {
        @Override
        public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            Course course = new Course();
            course.setId(rs.getInt("id"));
            course.setName(rs.getString("name"));
            course.setDurationWeeks(rs.getInt("duration_weeks"));
            return course;
        }
    };

    @Override
    public Course save(Course course) {
        String sql = "INSERT INTO courses (name, duration_weeks) " +
                "VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1,course.getName());
            ps.setInt(2,course.getDurationWeeks());
            return ps;
        },keyHolder);
        course.setId(keyHolder.getKey().intValue());
        return course;
    }

    @Override
    public Optional<Course> findById(Integer id) {
        String sql = "SELECT * FROM courses WHERE id = ? ";
        try{
            Course course = jdbcTemplate.queryForObject(
                    sql,
                    courseRowMapper,
                    id
            );
            return Optional.of(course);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Course> findAll() {
        String sql = "SELECT * FROM courses ORDER BY id";
        return jdbcTemplate.query(sql,courseRowMapper);
    }

    @Override
    public Optional<Course> findByName(String name) {
        String sql = "SELECT * FROM courses WHERE name = ? ";
        try{
            Course course = jdbcTemplate.queryForObject(
                    sql,
                    courseRowMapper,
                    name
            );
            return Optional.of(course);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Course> findByDuration(Integer duration) {
        String sql = "SELECT * FROM courses WHERE duration_weeks >= ?";

        return jdbcTemplate.query(sql, courseRowMapper, duration);
    }

    @Override
    public boolean update(Course course) {
        String sql = "UPDATE courses SET name=?,duration_weeks=? WHERE id = ? ";
        int rowsAffected = jdbcTemplate.update(
                sql,
                course.getName(),
                course.getDurationWeeks(),
                course.getId()
        );
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, id);

        return rowsAffected > 0;
    }

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM courses";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
