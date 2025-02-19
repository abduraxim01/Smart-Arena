package com.practise.Smart_Arena.model.owner;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.geo.Point;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class Stadium {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;

    private LocalTime workingHoursStart;

    private LocalTime workingHoursEnd;

    private Set<DayOfWeek> workingDays;

    private String locationName;

//    @Column(columnDefinition = "GEOMETRY(Point,4326)", nullable = false)
//    @JdbcTypeCode(SqlTypes.GEOMETRY)
    private Point locationPoint;

    private String rules;

    private Set<String> socialMediaAccounts;

    @CreationTimestamp
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private Owner owner;

    @OneToOne(mappedBy = "stadium", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Qulayliklar qulayliklar;

    @OneToMany(mappedBy = "stadium", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Polya> polyaList;
}
