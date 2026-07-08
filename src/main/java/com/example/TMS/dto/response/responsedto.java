package com.example.TMS.dto.response;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter @Setter @NoArgsConstructor@AllArgsConstructor @Builder
public class responsedto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private Set<RoleResponsedto> roles;

}
