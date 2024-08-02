package org.rahmi.gitproject.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String hash;
    private String message;
    private String repoName;


    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Override
    public String toString() {
        return "Commit{" +
                "hash='" + hash + '\'' +
                ", message='" + message + '\'' +
                ", repoName='" + repoName + '\'' +
                ", date=" + date +
                ", author=" + author +
                '}';
    }
}
