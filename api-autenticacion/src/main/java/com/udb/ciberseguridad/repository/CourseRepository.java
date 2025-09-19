package com.udb.ciberseguridad.repository;

import com.udb.ciberseguridad.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCategoria(String categoria);
    List<Course> findByOrderByCreatedAtDesc();
}