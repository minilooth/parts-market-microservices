package by.minilooth.vehicleservice.avby.beans;

import by.minilooth.vehicleservice.avby.beans.api.AbstractAvByProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AvBy2DArrayProperty<T> extends AbstractAvByProperty {

    private List<List<T>> value;

    public static <T> AvBy2DArrayPropertyBuilder<T> builder() {
        return new AvBy2DArrayPropertyBuilder<>();
    }

    public static class AvBy2DArrayPropertyBuilder<T> {

        private final AvBy2DArrayProperty<T> instance;

        private AvBy2DArrayPropertyBuilder() {
            this.instance = new AvBy2DArrayProperty<>();
        }

        public AvBy2DArrayPropertyBuilder<T> id(Long id) {
            this.instance.setId(id);
            return this;
        }

        public AvBy2DArrayPropertyBuilder<T> fallbackType(String fallbackType) {
            this.instance.setFallbackType(fallbackType);
            return this;
        }

        public AvBy2DArrayPropertyBuilder<T> valueFormat(String valueFormat) {
            this.instance.setValueFormat(valueFormat);
            return this;
        }

        public AvBy2DArrayPropertyBuilder<T> modified(Boolean modified) {
            this.instance.setModified(modified);
            return this;
        }

        public AvBy2DArrayPropertyBuilder<T> name(String name) {
            this.instance.setName(name);
            return this;
        }

        public AvBy2DArrayPropertyBuilder<T> property(Long property) {
            this.instance.setProperty(property);
            return this;
        }

        public AvBy2DArrayPropertyBuilder<T> previousValue(Object previousValue) {
            this.instance.setPreviousValue(previousValue);
            return this;
        }

        public AvBy2DArrayPropertyBuilder<T>options(Set<AvByOption> options) {
            this.instance.setOptions(options);
            return this;
        }

        public AvBy2DArrayPropertyBuilder<T> value(List<List<T>> value) {
            this.instance.setValue(value);
            return this;
        }

        public AvBy2DArrayProperty<T> build() {
            return instance;
        }

    }

}
