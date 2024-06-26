package ir.msob.jima.crud.sample.rsocket.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.msob.jima.core.commons.annotation.methodstats.MethodStats;
import ir.msob.jima.core.commons.model.dto.BaseDto;
import ir.msob.jima.core.commons.model.dto.BaseType;
import ir.msob.jima.core.commons.model.dto.ModelType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ObjectMapperSampleInitializer {

    private final ObjectMapper objectMapper;

    @MethodStats
    @PostConstruct
    public void initializeObjectMapper() {
        registerSubTypes();
    }

    private void registerSubTypes() {
        Set<String> prefixes = new HashSet<>();
        prefixes.add("ir.msob.jima.crud.sample.rsocket");
        prefixes.forEach(prefix -> {
            Reflections reflections = new Reflections(prefix);

            Set<Class<? extends BaseType>> baseTypeClasses = reflections.getSubTypesOf(BaseType.class);
            baseTypeClasses.forEach(objectMapper::registerSubtypes);

            Set<Class<? extends BaseDto>> baseDtoClasses = reflections.getSubTypesOf(BaseDto.class);
            baseDtoClasses.forEach(objectMapper::registerSubtypes);

            Set<Class<? extends ModelType>> modelTypeClasses = reflections.getSubTypesOf(ModelType.class);
            modelTypeClasses.forEach(objectMapper::registerSubtypes);
        });
    }
}
