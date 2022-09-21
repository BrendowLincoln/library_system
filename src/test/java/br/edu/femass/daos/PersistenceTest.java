package br.edu.femass.daos;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PersistenceTest {
    private final Persistence persistence = new Persistence();

    @Test
    @DisplayName("Deve retornar uma instância de ObjectMapper")
    public void should_return_an_ObjectMapper_instance() {
        //Given
        var expected = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //When
        var result = persistence.getObjectMapper();

        //Then
        result.getRegisteredModuleIds().forEach(moduleId ->  {
            Assertions.assertEquals(moduleId, expected.getRegisteredModuleIds().toArray()[0]);
        });

        Assertions.assertEquals(
            expected.getSerializationConfig().getSerializationFeatures(),
            result.getSerializationConfig().getSerializationFeatures()
        );
    }
}
