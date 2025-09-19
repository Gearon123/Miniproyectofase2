package com.udb.ciberseguridad.controller;

import com.udb.ciberseguridad.model.Course;
import com.udb.ciberseguridad.model.User;
import com.udb.ciberseguridad.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseRepository.findByOrderByCreatedAtDesc());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Course>> getCoursesByCategory(@PathVariable String categoria) {
        return ResponseEntity.ok(courseRepository.findByCategoria(categoria.toUpperCase()));
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course, @AuthenticationPrincipal User user) {
        if (!user.getRole().equals("DOCENTE")) {
            return ResponseEntity.status(403).build();
        }

        course.setDocente(user);
        return ResponseEntity.ok(courseRepository.save(course));
    }

    @GetMapping("/categorias")
    public ResponseEntity<Map<String, String>> getCategories() {
        return ResponseEntity.ok(Map.of(
                "CAUSAS", "Causas de problemas de ciberseguridad",
                "PROTECCION", "Formas de protegerse",
                "CONCEPTOS", "¿Qué es la ciberseguridad?"
        ));
    }
}