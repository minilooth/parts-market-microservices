package by.minilooth.vehicleservice.avby.beans.entities;

import lombok.Data;

import java.util.List;

@Data
public class AvByMake {

    private Long id;
    private String name;
    private List<AvByModel> models;

}
