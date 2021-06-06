package org.example;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "school")
@Data
public class SchoolProperties {

    private String name = "default_name";

    private String description = "default_description";


}
