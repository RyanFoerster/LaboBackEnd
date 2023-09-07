package be.techifutur.labo.adoptadev.exceptions;

public class NameNotFoundException extends RuntimeException{

    private final Object string;
    private final Class<?> resourceClass;

    public NameNotFoundException(Object string, Class<?> resourceClass) {
        super("Cannot find resource of type {%s} with {%s}".formatted(resourceClass.getSimpleName(), string.toString()));
        this.string = string;
        this.resourceClass = resourceClass;
    }

}
