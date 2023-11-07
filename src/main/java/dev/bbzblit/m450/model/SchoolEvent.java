package dev.bbzblit.m450.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class SchoolEvent {

    @Id
    @GeneratedValue
    private Long id;

    private ZonedDateTime appointmentStart;

    private ZonedDateTime appointmentEnd;

    @ManyToOne
    private SchoolClass classId;

    private String title;

    private String summary;

    private String place;



}
