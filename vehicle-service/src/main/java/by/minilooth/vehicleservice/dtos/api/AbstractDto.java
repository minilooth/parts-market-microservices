package by.minilooth.vehicleservice.dtos.api;

import by.minilooth.vehicleservice.common.consts.DateConsts;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AbstractDto implements BaseDto {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateConsts.DEFAULT_DATE_FORMAT)
    private Date updatedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateConsts.DEFAULT_DATE_FORMAT)
    private Date createdAt;

}
