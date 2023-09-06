package be.techifutur.labo.adoptadev.exceptions;

import lombok.Getter;

@Getter
public class ResourceAlreadyLinkedException extends RuntimeException {

    private final Class<?> containingClazz;
    private final Object containingId;
    private final Class<?> containedClazz;
    private final Object containedId;

    public ResourceAlreadyLinkedException(Class<?> containingClazz, Object containingId, Class<?> containedClazz, Object containedId) {
        super("{%s} with id {%s} already contains a {%s} with id {%s}".formatted(
                containingClazz.getSimpleName(),
                containingId.toString(),
                containedClazz.getSimpleName(),
                containedId.toString()
        ));
        this.containingClazz = containingClazz;
        this.containingId = containingId;
        this.containedClazz = containedClazz;
        this.containedId = containedId;
    }


}
