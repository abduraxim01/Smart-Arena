package com.practise.Smart_Arena.model.owner;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.practise.Smart_Arena.model.privileges.Role;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Valid
public class Owner implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Size(min = 3, message = "Name must be at least 3 characters")
    private String name;

    @Size(min = 3, message = "Surname must be at least 3 characters")
    private String surname;

    @NotNull
    private LocalDate birthday;

    @Size(min = 9, message = "Passport must be at least 9 characters")
    private String passport;

    @Size(min = 13, message = "Passport must be at least 13 characters")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private LocalDate createDate;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Stadium> stadiumList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

    @Override
    public String getPassword() {
        return "";
    }
}
