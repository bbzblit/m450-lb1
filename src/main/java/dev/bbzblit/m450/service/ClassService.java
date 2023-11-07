package dev.bbzblit.m450.service;

import dev.bbzblit.m450.model.SchoolClass;
import dev.bbzblit.m450.repository.ClassRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class ClassService implements ParentService<SchoolClass, Long> {

    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public SchoolClass save(SchoolClass entity) {
        return this.classRepository.save(entity);
    }

    @Override
    public SchoolClass findById(Long id) {
        return this.classRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404),"Class with id " + id + " not found")
        );
    }

    @Override
    public Iterable<SchoolClass> findAll() {
        return this.classRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.classRepository.deleteById(id);
    }
}
