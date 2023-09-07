package be.techifutur.labo.adoptadev.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final Object id;
    private final Class<?> resourceClass;

    public ResourceNotFoundException(Object id, Class<?> resourceClass) {
        super("Cannot find resource of type {%s} with id {%s}".formatted(resourceClass.getSimpleName(), id.toString()));
        this.id = id;
        this.resourceClass = resourceClass;
    }

}