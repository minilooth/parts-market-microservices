package by.minilooth.apigateway.models.api;

import lombok.Data;

import java.util.Date;

@Data
public abstract class AbstractEntity {

    private Long id;
    private Date createdAt;
    private Date updatedAt;

}
