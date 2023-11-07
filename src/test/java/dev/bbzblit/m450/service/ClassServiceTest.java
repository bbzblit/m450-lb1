package dev.bbzblit.m450.service;

import dev.bbzblit.m450.model.SchoolClass;
import dev.bbzblit.m450.repository.ClassRepository;
import dev.bbzblit.m450.service.ClassService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
public class ClassServiceTest {

    @Mock
    private ClassRepository classRepository;

    private ClassService classService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        classService = new ClassService(classRepository);
    }

    @Test
    public void testSave() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("INA 21-25");

        when(classRepository.save(schoolClass)).thenReturn(schoolClass);

        SchoolClass savedClass = classService.save(schoolClass);

        assertEquals(schoolClass, savedClass);
        verify(classRepository).save(schoolClass);
    }

    @Test
    public void testFindById() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("INA 21-25");

        when(classRepository.findById(1L)).thenReturn(Optional.of(schoolClass));

        SchoolClass foundClass = classService.findById(1L);

        assertEquals(schoolClass, foundClass);
        verify(classRepository).findById(1L);
    }

    @Test()
    public void testFindByIdNotFound() {
        when(classRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> {
            classService.findById(1L);
        });

    }

    @Test
    public void testFindAll() {
        SchoolClass schoolClass1 = new SchoolClass();
        schoolClass1.setId(1L);
        schoolClass1.setName("INA 21-25");

        SchoolClass schoolClass2 = new SchoolClass();
        schoolClass2.setId(2L);
        schoolClass2.setName("INB 21-25");

        List<SchoolClass> schoolClasses = Arrays.asList(schoolClass1, schoolClass2);

        when(classRepository.findAll()).thenReturn(schoolClasses);

        Iterable<SchoolClass> foundClasses = classService.findAll();

        assertEquals(schoolClasses, foundClasses);
        verify(classRepository).findAll();
    }

    @Test
    public void testDeleteById() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("INA 21-25");

        when(classRepository.findById(1L)).thenReturn(Optional.of(schoolClass));

        classService.deleteById(1L);

        verify(classRepository).deleteById(1L);
    }

    @Test()
    public void testDeleteByIdNotFound() {
        when(classRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            classService.deleteById(1L);
        });
    }

}