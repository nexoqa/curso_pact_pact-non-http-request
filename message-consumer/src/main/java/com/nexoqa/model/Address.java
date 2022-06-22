package com.nexoqa.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Address {

    String street;
    Integer number;
    String city;
    
}
