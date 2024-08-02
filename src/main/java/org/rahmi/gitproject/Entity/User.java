package org.rahmi.gitproject.Entity;


import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;

    @Setter
    @Getter
    @OneToMany(mappedBy = "author")
    private List<Commit> commits;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
