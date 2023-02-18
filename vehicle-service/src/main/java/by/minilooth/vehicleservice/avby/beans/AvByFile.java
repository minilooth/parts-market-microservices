package by.minilooth.vehicleservice.avby.beans;

import by.minilooth.vehicleservice.avby.beans.api.AbstractAvByEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.MimeType;

import java.net.URL;

@Data
@EqualsAndHashCode(callSuper = true)
public class AvByFile extends AbstractAvByEntity {

    private URL url;
    private MimeType mimeType;

}
