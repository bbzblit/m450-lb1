package dev.bbzblit.m450.model;


import jakarta.persistence.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
public class SchoolClass {

    private Long id;

    private String name;

}
