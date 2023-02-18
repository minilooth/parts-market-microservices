package by.minilooth.vehicleservice.avby.beans;

import lombok.Data;

import java.util.List;

@Data
public class AvByResponse {

    private List<AvBy2DArrayProperty<AvByProperty<Void>>> properties;

}
