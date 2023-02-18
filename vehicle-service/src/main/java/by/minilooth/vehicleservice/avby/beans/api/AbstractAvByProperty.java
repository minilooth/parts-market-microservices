package by.minilooth.vehicleservice.avby.beans.api;

import by.minilooth.vehicleservice.avby.beans.AvByOption;
import lombok.Data;

import java.util.Set;

@Data
public abstract class AbstractAvByProperty {

    private Long id;
    private String fallbackType;
    private String valueFormat;
    private boolean modified;
    private String name;
    private Long property;
    private Object previousValue;
    private Set<AvByOption> options;

}
