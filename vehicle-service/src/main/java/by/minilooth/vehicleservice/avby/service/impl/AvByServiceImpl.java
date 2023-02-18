package by.minilooth.vehicleservice.avby.service.impl;

import by.minilooth.vehicleservice.avby.beans.*;
import by.minilooth.vehicleservice.avby.beans.api.AbstractAvByProperty;
import by.minilooth.vehicleservice.avby.beans.entities.AvByGeneration;
import by.minilooth.vehicleservice.avby.beans.entities.AvByMake;
import by.minilooth.vehicleservice.avby.beans.entities.AvByModel;
import by.minilooth.vehicleservice.avby.consts.AvByConsts;
import by.minilooth.vehicleservice.avby.proxy.AvByProxy;
import by.minilooth.vehicleservice.avby.service.AvByService;
import by.minilooth.vehicleservice.common.enums.GenerationStatus;
import by.minilooth.vehicleservice.common.enums.MakeStatus;
import by.minilooth.vehicleservice.common.enums.ModelStatus;
import by.minilooth.vehicleservice.beans.Generation;
import by.minilooth.vehicleservice.beans.Make;
import by.minilooth.vehicleservice.beans.Model;
import by.minilooth.vehicleservice.repositories.GenerationRepository;
import by.minilooth.vehicleservice.repositories.MakeRepository;
import by.minilooth.vehicleservice.repositories.ModelRepository;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AvByServiceImpl implements AvByService {

    private final static PropertyUtilsBean PROPERTY_UTILS_BEAN = new PropertyUtilsBean();
    private final static Logger LOGGER = LoggerFactory.getLogger(AvByServiceImpl.class);

    @Autowired private AvByProxy avByProxy;
    @Autowired private MakeRepository makeRepository;
    @Autowired private ModelRepository modelRepository;
    @Autowired private GenerationRepository generationRepository;

//    @PostConstruct
    @Transactional
    public void load() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        LOGGER.info("Start fetching vehicles from av.by ...");

        long startTime = System.currentTimeMillis();
        long makesCount = 0L;
        long modelsCount = 0L;
        long generationsCount = 0L;

        List<AvByMake> makes = getMakes();
        makesCount = makesCount + makes.size();
        for (AvByMake make : makes) {
            List<AvByModel> models = getModels(make);
            make.setModels(models);
            modelsCount = modelsCount + models.size();
            for (AvByModel model : models) {
                List<AvByGeneration> generations = getGenerations(make, model);
                model.setGenerations(generations);
                generationsCount = generationsCount + generations.size();
            }
        }

        for (AvByMake avByMake : makes) {
            Make make = new Make();
            make.setName(avByMake.getName());
            make.setStatus(MakeStatus.ACTIVE);
            makeRepository.save(make);

            for (AvByModel avByModel : avByMake.getModels()) {
                Model model = new Model();
                model.setName(avByModel.getName());
                model.setStatus(ModelStatus.ACTIVE);
                model.setMake(make);
                modelRepository.save(model);

                for (AvByGeneration avByGeneration : avByModel.getGenerations()) {
                    Generation generation = new Generation();
                    generation.setName(avByGeneration.getName());
                    generation.setStatus(GenerationStatus.ACTIVE);
                    generation.setIssuedFrom(avByGeneration.getIssuedFrom());
                    generation.setIssuedTo(avByGeneration.getIssuedTo());
                    generation.setModel(model);
                    generationRepository.save(generation);
                }
            }
        }

        long finishTime = System.currentTimeMillis();
        float elapsedTime = (finishTime - startTime) / 1000.0f;

        LOGGER.info(String.format("Successfully fetched and saved: makes - %s, models - %s, generations - %s. Elapsed time: %ss", makesCount, modelsCount, generationsCount, elapsedTime));
    }

    @SuppressWarnings("unchecked")
    private List<AvByMake> getMakes() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        AvByResponse response = avByProxy.execute(buildMakesRequest());
        Set<AvByOption> options = (Set<AvByOption>)PROPERTY_UTILS_BEAN
                .getProperty(response, AvByConsts.Paths.MAKES_PATH);
        return options.stream()
                .map(option -> {
                    AvByMake make = new AvByMake();
                    make.setId(option.getId());
                    make.setName(option.getLabel());
                    return make;
                })
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private List<AvByModel> getModels(AvByMake make) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        AvByResponse response = avByProxy.execute(buildModelsRequest(make.getId()));
        Set<AvByOption> options = (Set<AvByOption>)PROPERTY_UTILS_BEAN
                .getProperty(response, AvByConsts.Paths.MODELS_PATH);
        return options.stream()
                .map(option -> {
                    AvByModel model = new AvByModel();
                    model.setId(option.getId());
                    model.setName(option.getLabel());
                    return model;
                })
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private List<AvByGeneration> getGenerations(AvByMake make, AvByModel model) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        AvByResponse response = avByProxy.execute(buildGenerationsRequest(make.getId(), model.getId()));
        Set<AvByOption> options = (Set<AvByOption>)PROPERTY_UTILS_BEAN
                .getProperty(response, AvByConsts.Paths.GENERATIONS_PATH);
        return options.stream()
                .map(option -> {
                    AvByGeneration generation = new AvByGeneration();
                    generation.setId(option.getId());
                    generation.setName(option.getLabel());

                    if (Objects.nonNull(option.getMetadata())) {
                        generation.setIssuedFrom(option.getMetadata().getYearFrom());
                        generation.setIssuedTo(option.getMetadata().getYearTo());
                    }

                    return generation;
                })
                .collect(Collectors.toList());
    }

    private AvByRequest buildMakesRequest() {
        /*
            {
                "properties": [
                    {
                        "modified": true,
                        "name": "brands",
                        "property": 5,
                        "value": [[]]
                    },
                    {
                        "name": "price_currency",
                        "value": 2
                    }
                ]
            }
         */
        AbstractAvByProperty brandsProperty = AvBy2DArrayProperty.builder()
                .name(AvByConsts.PropertyNames.BRANDS_PROPERTY_NAME)
                .property(AvByConsts.PropertyIds.BRANDS_PROPERTY_ID)
                .value(Collections.singletonList(Collections.emptyList()))
                .modified(true)
                .build();

        AbstractAvByProperty priceCurrencyProperty = buildPriceCurrencyProperty();

        return AvByRequest.builder()
                .properties(List.of(brandsProperty, priceCurrencyProperty))
                .build();
    }

    private AvByRequest buildModelsRequest(Long brandId) {
        /*
            {
                "properties": [
                    {
                        "modified": true,
                        "name": "brands",
                        "property": 5,
                        "value": [
                            [
                                {
                                    "name": "brand",
                                    "value": 9999999,
                                    "modified": true,
                                    "previousValue": null
                                }
                            ]
                        ]
                    },
                    {
                        "name": "price_currency",
                        "value": 2
                    }
                ]
            }
         */
        AbstractAvByProperty brandProperty = AvByProperty.<Long>builder()
                .name(AvByConsts.PropertyNames.BRAND_PROPERTY_NAME)
                .value(brandId)
                .modified(true)
                .build();

        AbstractAvByProperty brandsProperty = AvBy2DArrayProperty.builder()
                .name(AvByConsts.PropertyNames.BRANDS_PROPERTY_NAME)
                .property(AvByConsts.PropertyIds.BRANDS_PROPERTY_ID)
                .value(Collections.singletonList(List.of(brandProperty)))
                .modified(true)
                .build();

        AbstractAvByProperty priceCurrencyProperty = buildPriceCurrencyProperty();

        return AvByRequest.builder()
                .properties(List.of(brandsProperty, priceCurrencyProperty))
                .build();
    }

    private AvByRequest buildGenerationsRequest(Long brandId, Long modelId) {
        /*
            {
                "properties": [
                    {
                        "modified": true,
                        "name": "brands",
                        "property": 5,
                        "value": [
                            [
                                {
                                    "name": "brand",
                                    "value": 1000001
                                },
                                {
                                    "name": "model",
                                    "value": 9999999,
                                    "modified": true,
                                    "previousValue": null
                                }
                            ]
                        ]
                    },
                    {
                        "name": "price_currency",
                        "value": 2
                    }
                ]
            }
         */
        AbstractAvByProperty brandProperty = AvByProperty.<Long>builder()
                .name(AvByConsts.PropertyNames.BRAND_PROPERTY_NAME)
                .value(brandId)
                .build();

        AbstractAvByProperty modelProperty = AvByProperty.<Long>builder()
                .name(AvByConsts.PropertyNames.MODEL_PROPERTY_NAME)
                .value(modelId)
                .modified(true)
                .build();

        AbstractAvByProperty brandsProperty = AvBy2DArrayProperty.builder()
                .name(AvByConsts.PropertyNames.BRANDS_PROPERTY_NAME)
                .property(AvByConsts.PropertyIds.BRANDS_PROPERTY_ID)
                .value(Collections.singletonList(List.of(brandProperty, modelProperty)))
                .modified(true)
                .build();

        AbstractAvByProperty priceCurrencyProperty = buildPriceCurrencyProperty();

        return AvByRequest.builder()
                .properties(List.of(brandsProperty, priceCurrencyProperty))
                .build();
    }

    private AbstractAvByProperty buildPriceCurrencyProperty() {
        return AvByProperty.<Long>builder()
                .name(AvByConsts.PropertyNames.PRICE_CURRENCY_PROPERTY_NAME)
                .value(AvByConsts.PropertyIds.PRICE_CURRENCY_PROPERTY_ID)
                .build();
    }

}
