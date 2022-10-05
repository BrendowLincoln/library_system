package br.edu.femass.daos;

import br.edu.femass.utils.GlobalConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    protected Long nextEntityIndex(String entity) throws IOException {
        List<PersistenceIndex> entitiesIndex;

        try {
            FileInputStream in = new FileInputStream(FILE_NAME);
            String json = new String(in.readAllBytes());
            in.close();
            entitiesIndex = getObjectMapper().readValue(json, new TypeReference<List<PersistenceIndex>>() {  });

        } catch (IOException f) {
            entitiesIndex = new ArrayList<PersistenceIndex>();
        }

        boolean isEntityExist = entitiesIndex.stream().map(index -> index.getEntity()).collect(Collectors.toList()).contains(entity);

        PersistenceIndex persistenceIndex;

        if (!isEntityExist) {
            persistenceIndex = new PersistenceIndex(entity, 1L);
            entitiesIndex.add(persistenceIndex);
            String json = getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entitiesIndex);
            FileOutputStream out = new FileOutputStream(FILE_NAME);
            out.write(json.getBytes());
            out.close();
            return persistenceIndex.getCurrentIndex();
        } else {
            persistenceIndex = entitiesIndex.stream().filter(index -> index.getEntity().equals(entity)).findFirst().get();

            Long nextIndex = persistenceIndex.getCurrentIndex() + 1L;

            PersistenceIndex updatedPersistenceIndex = new PersistenceIndex(entity, nextIndex);

            for(int i = 0; i < entitiesIndex.size(); i++) {
                if(entitiesIndex.get(i).getEntity().equals(entity)) {
                    entitiesIndex.set(i, updatedPersistenceIndex);
                    String json = getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entitiesIndex);
                    FileOutputStream out = new FileOutputStream(FILE_NAME);
                    out.write(json.getBytes());
                    out.close();
                    return nextIndex;
                }
            }
        }
        return null;
    }
}

class PersistenceIndex {
    private String entity;
    private Long currentIndex;

    public PersistenceIndex() { }

    public  PersistenceIndex(String entity, Long currentIndex) {
        this.entity = entity;
        this.currentIndex = currentIndex;
    }

    public String getEntity() {
        return this.entity;
    }

    public Long getCurrentIndex() {
        return this.currentIndex;
    }
}