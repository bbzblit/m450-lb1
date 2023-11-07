package dev.bbzblit.m450.controller;

import dev.bbzblit.m450.model.SchoolClass;
import dev.bbzblit.m450.model.SchoolEvent;
import dev.bbzblit.m450.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(final EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping("/appointment")
    public Iterable<SchoolEvent> getAppointmentsOfClass(@RequestParam("classId") Long classId){
        return this.eventService.getAllEventsOfClass(classId);
    }

}
