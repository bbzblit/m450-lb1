package dev.bbzblit.m450.controller;

import dev.bbzblit.m450.model.SchoolClass;
import dev.bbzblit.m450.service.ClassService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassController {
    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping("/class")
    public Iterable<SchoolClass> getAllClasses() {
        return this.classService.findAll();
    }
}
