package be.techifutur.labo.adoptadev.validations.validators;

import be.techifutur.labo.adoptadev.validations.constraints.TimesAgo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class TimesAgoValidator implements ConstraintValidator<TimesAgo, LocalDate> {
    private TimesAgo annotation;

    @Override
    public void initialize(TimesAgo constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        int yearsAgo = annotation.years();
        LocalDate ref = LocalDate.now().minusYears(yearsAgo);
        return value.isBefore( ref );
    }
}
