package by.minilooth.vehicleservice.avby.beans.entities;

import lombok.Data;

@Data
public class AvByGeneration {

    private Long id;
    private String name;
    private Integer issuedFrom;
    private Integer issuedTo;

}
