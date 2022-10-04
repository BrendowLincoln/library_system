package br.edu.femass.daos;

import br.edu.femass.models.Author;
import br.edu.femass.utils.GlobalConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Persistence {
    private final String FILE_NAME = GlobalConstants.PERSISTENCE_DIRECTORY_PATH + "persistence_index.json";

    protected ObjectMapper getObjectMapper() {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    protected Integer nextEntityIndex(String entity) {
        List<PersistenceIndex> entitiesIndex;

        try {
            FileInputStream in = new FileInputStream(FILE_NAME);
            String json = new String(in.readAllBytes());
            in.close();
            entitiesIndex = getObjectMapper().readValue(json, new TypeReference<List<PersistenceIndex>>() {
            });

        } catch (IOException f) {
            return null;
        }

        boolean isEntityExist = entitiesIndex.stream().map(index -> index.entity).collect(Collectors.toList()).contains(entity);

        PersistenceIndex persistenceIndex;

        if (!isEntityExist) {
            persistenceIndex = new PersistenceIndex(entity, 0);
            entitiesIndex.add(persistenceIndex);
        } else {
            persistenceIndex = entitiesIndex.stream().filter(index -> index.entity == entity).findFirst().get();
            PersistenceIndex updatedPersistenceIndex = new PersistenceIndex(entity, persistenceIndex.currentIndex++);

            for(int i = 0; i < 0; i++) {
                if(persistenceIndex.entity == entity) {
                    entitiesIndex.set(i, updatedPersistenceIndex);

                    return updatedPersistenceIndex.currentIndex;
                }
            }
        }

        return null;
    }
}

class PersistenceIndex {
    public String entity;
    public Integer currentIndex;

    public PersistenceIndex() { }

    public  PersistenceIndex(String entity, Integer currentIndex) {
        this.entity = entity;
        this.currentIndex = currentIndex;
    }

    public String getEntity() {
        return this.entity;
    }

    public Integer getCurrentIndex() {
        return this.currentIndex;
    }
}