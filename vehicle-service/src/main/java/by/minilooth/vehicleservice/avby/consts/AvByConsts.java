package by.minilooth.vehicleservice.avby.consts;

public class AvByConsts {

    public static class PropertyNames {

        public static final String BRAND_PROPERTY_NAME = "brand";
        public static final String MODEL_PROPERTY_NAME = "model";
        public static final String BRANDS_PROPERTY_NAME = "brands";
        public static final String PRICE_CURRENCY_PROPERTY_NAME = "price_currency";

    }

    public static class PropertyIds {

        public static final Long BRANDS_PROPERTY_ID = 5L;
        public static final Long PRICE_CURRENCY_PROPERTY_ID = 2L;

    }

    public static class Paths {

        public static final String MAKES_PATH = "properties[0].value[0][1].options";
        public static final String MODELS_PATH = "properties[0].value[0][2].options";
        public static final String GENERATIONS_PATH = "properties[0].value[0][3].options";

    }

}
