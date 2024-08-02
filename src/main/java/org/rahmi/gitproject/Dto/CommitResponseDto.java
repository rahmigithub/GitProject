package org.rahmi.gitproject.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommitResponseDto {

    private String sha;
    private CommitDto commit;


}
