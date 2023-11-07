package dev.bbzblit.m450.service;

import dev.bbzblit.m450.model.SchoolClass;
import dev.bbzblit.m450.model.SchoolEvent;
import dev.bbzblit.m450.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    
    private EventService eventService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventService(eventRepository);
    }

    @Test
    public void testSave() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("Math");

        SchoolEvent schoolEvent = new SchoolEvent();
        schoolEvent.setId(1L);
        schoolEvent.setTitle("Test Event");
        schoolEvent.setClassId(schoolClass);
        schoolEvent.setAppointmentStart(Instant.now());
        schoolEvent.setAppointmentEnd(Instant.now().plusSeconds(3600));

        when(eventRepository.save(schoolEvent)).thenReturn(schoolEvent);

        SchoolEvent savedEvent = eventService.save(schoolEvent);

        assertEquals(schoolEvent, savedEvent);
        verify(eventRepository).save(schoolEvent);
    }

    @Test
    public void testFindById() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("Math");

        SchoolEvent schoolEvent = new SchoolEvent();
        schoolEvent.setId(1L);
        schoolEvent.setTitle("Test Event");
        schoolEvent.setClassId(schoolClass);
        schoolEvent.setAppointmentStart(Instant.now());
        schoolEvent.setAppointmentEnd(Instant.now().plusSeconds(3600));

        when(eventRepository.findById(1L)).thenReturn(Optional.of(schoolEvent));

        SchoolEvent foundEvent = eventService.findById(1L);

        assertEquals(schoolEvent, foundEvent);
        verify(eventRepository).findById(1L);
    }

    @Test()
    public void testFindByIdNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> {
            eventService.findById(1L);
        });

    }

    @Test
    public void testFindAll() {
        SchoolClass schoolClass1 = new SchoolClass();
        schoolClass1.setId(1L);
        schoolClass1.setName("Math");

        SchoolClass schoolClass2 = new SchoolClass();
        schoolClass2.setId(2L);
        schoolClass2.setName("Science");

        SchoolEvent schoolEvent1 = new SchoolEvent();
        schoolEvent1.setId(1L);
        schoolEvent1.setTitle("Test Event 1");
        schoolEvent1.setClassId(schoolClass1);
        schoolEvent1.setAppointmentStart(Instant.now());
        schoolEvent1.setAppointmentEnd(Instant.now().plusSeconds(3600));

        SchoolEvent schoolEvent2 = new SchoolEvent();
        schoolEvent2.setId(2L);
        schoolEvent2.setTitle("Test Event 2");
        schoolEvent2.setClassId(schoolClass2);
        schoolEvent2.setAppointmentStart(Instant.now());
        schoolEvent2.setAppointmentEnd(Instant.now().plusSeconds(3600));

        List<SchoolEvent> schoolEvents = Arrays.asList(schoolEvent1, schoolEvent2);

        when(eventRepository.findAll()).thenReturn(schoolEvents);

        Iterable<SchoolEvent> foundEvents = eventService.findAll();

        assertEquals(schoolEvents, foundEvents);
        verify(eventRepository).findAll();
    }

    @Test
    public void testDeleteById() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("Math");

        SchoolEvent schoolEvent = new SchoolEvent();
        schoolEvent.setId(1L);
        schoolEvent.setTitle("Test Event");
        schoolEvent.setClassId(schoolClass);
        schoolEvent.setAppointmentStart(Instant.now());
        schoolEvent.setAppointmentEnd(Instant.now().plusSeconds(3600));

        when(eventRepository.findById(1L)).thenReturn(Optional.of(schoolEvent));

        eventService.deleteById(1L);

        verify(eventRepository).deleteById(1L);
    }

    @Test()
    public void testDeleteByIdNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            eventService.deleteById(1L);
        });
    }

    @Test
    public void testGetAllEventsOfClass() {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("Math");

        SchoolEvent schoolEvent1 = new SchoolEvent();
        schoolEvent1.setId(1L);
        schoolEvent1.setTitle("Test Event 1");
        schoolEvent1.setClassId(schoolClass);
        schoolEvent1.setAppointmentStart(Instant.now());
        schoolEvent1.setAppointmentEnd(Instant.now().plusSeconds(3600));

        SchoolEvent schoolEvent2 = new SchoolEvent();
        schoolEvent2.setId(2L);
        schoolEvent2.setTitle("Test Event 2");
        schoolEvent2.setClassId(schoolClass);
        schoolEvent2.setAppointmentStart(Instant.now());
        schoolEvent2.setAppointmentEnd(Instant.now().plusSeconds(3600));

        List<SchoolEvent> schoolEvents = Arrays.asList(schoolEvent1, schoolEvent2);

        when(eventRepository.findAllByClassIdId(1L)).thenReturn(schoolEvents);

        Iterable<SchoolEvent> foundEvents = eventService.getAllEventsOfClass(1L);

        assertEquals(schoolEvents, foundEvents);
        verify(eventRepository).findAllByClassIdId(1L);
    }
}