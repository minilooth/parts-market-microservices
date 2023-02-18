package by.minilooth.vehicleservice.avby.beans;

import by.minilooth.vehicleservice.avby.beans.api.AbstractAvByProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvByRequest {

    private List<AbstractAvByProperty> properties;

}
