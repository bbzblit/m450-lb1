package dev.bbzblit.m450.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class SchoolRoom {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
