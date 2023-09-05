package be.techifutur.labo.adoptadev.exceptions;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;

@Getter
public class UniqueViolationException extends RuntimeException{

    private final Collection<String> fieldsNames;

    public UniqueViolationException(String... fieldsNames){
        this(Arrays.stream(fieldsNames).toList(), null);
    }

    public UniqueViolationException(Collection<String> fieldsNames) {
        this(fieldsNames, null);
    }

    public UniqueViolationException(Collection<String> fieldsNames, Throwable cause) {
        super("{%s} should be unique".formatted(fieldsNames), cause);
        this.fieldsNames = fieldsNames;
    }

}
