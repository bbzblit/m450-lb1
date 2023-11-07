package dev.bbzblit.m450.loader;

import dev.bbzblit.m450.model.SchoolEvent;
import dev.bbzblit.m450.model.SchoolRoom;
import dev.bbzblit.m450.repository.EventRepository;
import dev.bbzblit.m450.service.ClassService;
import dev.bbzblit.m450.service.EventService;
import dev.bbzblit.m450.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class StundenplanLoader {

    private final EventService eventService;
    private final ClassService classService;

    private final RoomService roomService;

    public StundenplanLoader(EventService eventService, ClassService classService, RoomService roomService) {
        this.eventService = eventService;
        this.classService = classService;
        this.roomService = roomService;
    }

    public void load(final String icanContent) {
        SchoolEvent event = null;
        for (String line : icanContent.split("\n")) {

            String content = line.substring(line.indexOf(":") + 1);

            if (line.startsWith("BEGIN:VEVENT")) {
                event = new SchoolEvent();
            } else if (line.startsWith("END:VEVENT")) {
                this.eventService.save(event);
            } else if (event == null){
            } else if (line.startsWith("DTSTART:")) {
                event.setAppointmentStart(Instant.ofEpochSecond(Long.parseLong(content)));
            } else if (line.startsWith("DTEND:")) {
                event.setAppointmentEnd(Instant.ofEpochSecond(Long.parseLong(content)));
            } else if (line.startsWith("SUMMARY:")) {
                event.setSummary(content);
            } else if (line.startsWith("LOCATION:")) {
                SchoolRoom room;
                try {
                    room = this.roomService.findById(Long.parseLong(content));
                } catch (ResponseStatusException ex) {
                    room = new SchoolRoom();
                    room.setName(content);
                    room = this.roomService.save(room);
                }
                event.setPlace(room);
            } else if (line.startsWith("DESCRIPTION:")) {
                event.setTitle(content);
            } else if (line.startsWith("UID:")) {
                Long classId =Long.parseLong(content.split("@")[0]);
                try{
                    event.setClassId(this.classService.findById(classId));
                } catch (ResponseStatusException ex) {
                    throw new RuntimeException("Class with id " + classId + " not found. You have to load the class first");
                }
            }
        }
    }
}
