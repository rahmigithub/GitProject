package org.rahmi.gitproject.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitDto {

    private String message;
    private UserDto author;
}
