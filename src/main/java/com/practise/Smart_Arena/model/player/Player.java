package com.practise.Smart_Arena.model.player;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.practise.Smart_Arena.model.privileges.Permissions;
import com.practise.Smart_Arena.model.privileges.Role;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@Builder
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class Player implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Size(min = 3, message = "Name must be at least 3 characters")
    private String name;

    @Size(min = 3, message = "Name must be at least 3 characters")
    private String surname;

    @Size(min = 13, message = "Passport must be at least 13 characters")
    private String phoneNumber;

    private LocalDate birthday;

    private String birthdayPlace;

    private String residence;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> commentList;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Match> matchList;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    private boolean isTeamOwner; // true: if has own team

    private boolean isOpenJoin; // true: anyone can add to team

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Set<Permissions> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>(
                Set.of(new SimpleGrantedAuthority("ROLE_" + role.name())));

        if (permissions != null) {
            authorities.addAll(permissions.stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.name()))
                    .toList());
        }
        return authorities;
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
