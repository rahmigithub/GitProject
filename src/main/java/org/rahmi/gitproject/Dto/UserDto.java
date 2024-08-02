package org.rahmi.gitproject.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private Date date;

}
