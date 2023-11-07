package dev.bbzblit.m450.loader;

import dev.bbzblit.m450.model.SchoolClass;
import dev.bbzblit.m450.model.SchoolEvent;
import dev.bbzblit.m450.model.SchoolRoom;
import dev.bbzblit.m450.service.ClassService;
import dev.bbzblit.m450.service.EventService;
import dev.bbzblit.m450.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StundenplanLoaderTest {

    @Mock
    private EventService eventService;

    @Mock
    private ClassService classService;

    @Mock
    private RoomService roomService;


    private StundenplanLoader stundenplanLoader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stundenplanLoader = new StundenplanLoader(eventService, classService, roomService);
    }

    /* =============================================================================================
     * Test the method load(String icalContent) of the class StundenplanLoader
     * =============================================================================================
     */

    @Test
    public void testLoad() {

        // Arrange
        String icalContent = "BEGIN:VEVENT\n" +
                "DTSTART:20220101T090000Z\n" +
                "DTEND:20220101T100000Z\n" +
                "SUMMARY:Test Event\n" +
                "LOCATION:P-14\n" +
                "DESCRIPTION:This is a Test\n" +
                "UID:1@test.com\n" +
                "END:VEVENT\n";

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("Math");

        SchoolRoom schoolRoom = new SchoolRoom();
        schoolRoom.setId(1L);
        schoolRoom.setName("P-14");

        SchoolEvent schoolEvent = new SchoolEvent();
        schoolEvent.setAppointmentStart(Instant.parse("2022-01-01T09:00:00Z"));
        schoolEvent.setAppointmentEnd(Instant.parse("2022-01-01T10:00:00Z"));
        schoolEvent.setTitle("Test Event");
        schoolEvent.setPlace(schoolRoom);
        schoolEvent.setSummary("This is a Test");
        schoolEvent.setClassId(schoolClass);

        when(classService.findById(1L)).thenReturn(schoolClass);
        when(roomService.findByName("P-14")).thenReturn(schoolRoom);

        // Act
        stundenplanLoader.load(icalContent);

        // Assert
        verify(eventService, times(1)).save(schoolEvent);
    }

    /* =============================================================================================
     * Test the method load(String icalContent) of the class StundenplanLoader
     * =============================================================================================
     */

    @Test
    public void testLoadClassNotFound() {

        //Arrange
        String icalContent = "BEGIN:VEVENT\n" +
                "DTSTART:20220101T090000Z\n" +
                "DTEND:20220101T100000Z\n" +
                "SUMMARY:Test Event\n" +
                "LOCATION:P-14\n" +
                "DESCRIPTION:This is a Test\n" +
                "UID:1@test.com\n" +
                "END:VEVENT\n";

        when(classService.findById(1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));

        //Act
        try {
            stundenplanLoader.load(icalContent);
        } catch (RuntimeException ex) {
            //Assert
            assertEquals(ex.getMessage(), "Class with id 1 not found. You have to load the class first");
            verify(eventService, never()).save(any());
            return;
        }

        // Assert
        throw new AssertionError("Expected ResponseStatusException was not thrown");
    }

    /* =============================================================================================
     * Test the method load(String icalContent) of the class StundenplanLoader
     * =============================================================================================
     */

    @Test
    public void testLoadRoomNotFound() {
        //Arrange
        String icalContent = "BEGIN:VEVENT\n" +
                "DTSTART:20230815T071500\n" +
                "DTEND:20220101T100000Z\n" +
                "SUMMARY:Test Event\n" +
                "LOCATION:P-14\n" +
                "DESCRIPTION:This is a Test\n" +
                "UID:1@test.com\n" +
                "END:VEVENT\n";

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(1L);
        schoolClass.setName("Math");

        SchoolRoom schoolRoom = new SchoolRoom();
        schoolRoom.setName("P-14");

        when(classService.findById(1L)).thenReturn(schoolClass);
        when(roomService.save(schoolRoom)).thenReturn(SchoolRoom.builder().id(1L).name("P-14").build());
        when(roomService.findByName("P-14")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

        //Act
        stundenplanLoader.load(icalContent);

        //Assert
        verify(roomService, times(1)).save(schoolRoom);
    }
}