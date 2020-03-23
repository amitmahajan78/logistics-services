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
public class Location {
    String locationId;
    String locationName;
    String locationType;
    String locationRole;
}
