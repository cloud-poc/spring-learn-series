package org.akj.springboot.validator;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.domain.BaseEntity;
import org.akj.springboot.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ServerWebInputException;

@Component
@Slf4j
public class GenericValidator {
    private Validator validator;

    public GenericValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(BaseEntity entity) {
        log.debug("start to validate object: {}", entity);
        Errors errors = new BeanPropertyBindingResult(entity, "entity");
        validator.validate(entity, errors);

        if (errors.hasErrors()) {
            log.error("validation errors, entity: {}, errors: {}", entity, errors);
            throw new ServerWebInputException(errors.toString());
        }
    }
}
