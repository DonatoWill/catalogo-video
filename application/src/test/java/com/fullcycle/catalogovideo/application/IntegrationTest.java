package com.fullcycle.catalogovideo.application;

import com.fullcycle.catalogovideo.application.configuration.WebServerConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited //Utilizado par caso alguém queira extender o comportamento da anotação
@ActiveProfiles("test")
@SpringBootTest(classes = WebServerConfig.class)
@ExtendWith(CleanUpExtension.class)
public @interface IntegrationTest {


}
