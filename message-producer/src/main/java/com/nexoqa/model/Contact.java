package com.nexoqa.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Contact {

    private int contactId;
    private String firstName;
    private String lastName;

}

