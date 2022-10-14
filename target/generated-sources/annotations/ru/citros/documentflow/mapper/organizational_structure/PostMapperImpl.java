package ru.citros.documentflow.mapper.organizational_structure;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.citros.documentflow.dto.organizational_structure.PostDto;
import ru.citros.documentflow.model.organizational_structure.Post;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-15T00:45:17+0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post fromDto(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( postDto.getId() );
        post.setName( postDto.getName() );

        return post;
    }

    @Override
    public PostDto toDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setId( post.getId() );
        postDto.setName( post.getName() );

        return postDto;
    }
}
