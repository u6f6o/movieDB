package com.u6f6o.apps.moviedb.core.validation;

import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class BeforeSafeValidator extends AbstractMongoEventListener {

    private final Validator entityValidator;

    @Autowired
    public BeforeSafeValidator(Validator entityValidator) {
        this.entityValidator = entityValidator;
    }


    @Override
    public void onBeforeSave( Object source, DBObject dbo ) {
        Set violations = entityValidator.validate( source );

        if ( violations.size() > 0 ) {
            throw new ConstraintViolationException( violations );
        }
    }
}
