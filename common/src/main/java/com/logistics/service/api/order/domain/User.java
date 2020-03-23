package com.logistics.service.api.order.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    UUID id;
    String userId;
    String name;
    String type;
    String role;
}
