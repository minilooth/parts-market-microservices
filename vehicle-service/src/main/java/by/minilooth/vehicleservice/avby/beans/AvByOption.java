package by.minilooth.vehicleservice.avby.beans;

import by.minilooth.vehicleservice.avby.beans.api.AbstractAvByEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AvByOption extends AbstractAvByEntity {

    private String label;
    private AvByMetadata metadata;

    @JsonProperty("intValue")
    private Long value;

}
