package by.minilooth.vehicleservice.avby.beans;

import by.minilooth.vehicleservice.avby.beans.api.AbstractAvByProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AvByProperty<T> extends AbstractAvByProperty {

    private T value;

    public static <T> AvByPropertyBuilder<T> builder() {
        return new AvByPropertyBuilder<>();
    }

    public static class AvByPropertyBuilder<T> {

        private final AvByProperty<T> instance;

        private AvByPropertyBuilder() {
            this.instance = new AvByProperty<>();
        }

        public AvByPropertyBuilder<T> id(Long id) {
            this.instance.setId(id);
            return this;
        }

        public AvByPropertyBuilder<T> fallbackType(String fallbackType) {
            this.instance.setFallbackType(fallbackType);
            return this;
        }

        public AvByPropertyBuilder<T> valueFormat(String valueFormat) {
            this.instance.setValueFormat(valueFormat);
            return this;
        }

        public AvByPropertyBuilder<T> modified(Boolean modified) {
            this.instance.setModified(modified);
            return this;
        }

        public AvByPropertyBuilder<T> name(String name) {
            this.instance.setName(name);
            return this;
        }

        public AvByPropertyBuilder<T> property(Long property) {
            this.instance.setProperty(property);
            return this;
        }

        public AvByPropertyBuilder<T> previousValue(Object previousValue) {
            this.instance.setPreviousValue(previousValue);
            return this;
        }

        public AvByPropertyBuilder<T> options(Set<AvByOption> options) {
            this.instance.setOptions(options);
            return this;
        }

        public AvByPropertyBuilder<T> value(T value) {
            this.instance.setValue(value);
            return this;
        }

        public AvByProperty<T> build() {
            return instance;
        }

    }

}
