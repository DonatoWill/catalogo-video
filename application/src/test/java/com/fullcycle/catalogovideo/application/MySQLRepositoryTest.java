package com.fullcycle.catalogovideo.application;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited //Utilizado par caso alguém queira extender o comportamento da anotação
@ActiveProfiles("test")
@DataJpaTest
@ExtendWith(CleanUpExtension.class)
@ComponentScan("com.fullcycle.catalogovideo.infrastructure.category")
public @interface MySQLRepositoryTest {


}
