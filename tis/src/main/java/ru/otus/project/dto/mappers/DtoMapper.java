package ru.otus.project.dto.mappers;

/**
 * Интерфейс для мапперов DTO / Entity
 *
 * @param <D> - DTO
 * @param <E> - Entity
 */
public interface DtoMapper<D, E> {

    /**
     * DTO to Entity
     */
    D toDto(E source);

    /**
     * Entity to DTO
     */
    E toModel(D source);

}
