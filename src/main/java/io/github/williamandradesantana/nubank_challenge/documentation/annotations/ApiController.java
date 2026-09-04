package io.github.williamandradesantana.nubank_challenge.documentation.annotations;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@Tag(name = "")
public @interface ApiController {

    @AliasFor(annotation = Tag.class, attribute = "name")
    String tagName();

    @AliasFor(annotation = Tag.class, attribute = "description")
    String tagDescription() default "";
}
