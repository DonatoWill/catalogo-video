package com.fullcycle.catalogovideo.infrastructure.data;

import com.fullcycle.catalogovideo.infrastructure.persistence.CategoryPersistence;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringDataICategoryRepositoryTests {

    @Autowired
    private SpringDataCategoryRepository repository;

    @Test
    void saveCategory() {
        CategoryPersistence input = new CategoryPersistence();
        input.setName("Action");
        input.setDescription("Action Description");
        input.setIsActive(true);

        CategoryPersistence actual = repository.save(input);

        assertNotNull(actual);
        assertThat(actual).hasFieldOrPropertyWithValue("name", "Action");
        assertTrue(actual.getIsActive());
    }
}
