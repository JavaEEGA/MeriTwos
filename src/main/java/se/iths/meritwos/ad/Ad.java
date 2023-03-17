package se.iths.meritwos.ad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Ad {
    @Id
    @NonNull
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String company;
    private String description;
}