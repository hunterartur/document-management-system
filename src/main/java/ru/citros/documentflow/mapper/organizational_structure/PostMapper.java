package ru.citros.documentflow.mapper.organizational_structure;

import org.mapstruct.Mapper;
import ru.citros.documentflow.dto.organizational_structure.PostDto;
import ru.citros.documentflow.model.organizational_structure.Post;

/**
 * Маппинг объекта
 *
 * @author AIshmaev
 */
@Mapper(componentModel = "spring")
public interface PostMapper {

    /**
     * Маппит тип объекта отправки клиенту к типу объекта приложения
     *
     * @param postDto объект отправки клиенту
     * @return объект приложения
     */
    Post fromDto(PostDto postDto);

    /**
     * Маппит тип объекта приложения к типу объекта отправки клиенту
     *
     * @param post объект приложения
     * @return объект отправки клиенту
     */
    PostDto toDto(Post post);
}
