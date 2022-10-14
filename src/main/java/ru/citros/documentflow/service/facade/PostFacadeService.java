package ru.citros.documentflow.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.citros.documentflow.dto.organizational_structure.PostDto;
import ru.citros.documentflow.exception.DocumentFlowException;
import ru.citros.documentflow.mapper.organizational_structure.PostMapper;
import ru.citros.documentflow.model.organizational_structure.Post;
import ru.citros.documentflow.repository.GeneralRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Слой приложения для работы с dto
 *
 * @author AIshmaev
 */
@Service
public class PostFacadeService {

    @Autowired
    private GeneralRepository<Post> postRepository;

    @Autowired
    private PostMapper postMapper;

    /**
     * Сохраняет объект
     *
     * @param postDto объект клиента
     * @return объект клиента
     */
    public PostDto save(PostDto postDto) {
        Post post = postMapper.fromDto(postDto);
        Post savedPost = postRepository.save(post);
        return postMapper.toDto(savedPost);
    }

    /**
     * Обновляет объект
     *
     * @param postDto объект клиента
     * @return объект клиента
     */
    public PostDto update(PostDto postDto) {
        if (postDto.getId() == null) {
            throw new DocumentFlowException("У должности нет id");
        }
        Post post = postMapper.fromDto(postDto);
        Post savedPost = postRepository.update(post);
        return postMapper.toDto(savedPost);
    }

    /**
     * Возвращает список объектов
     *
     * @return список объектов
     */
    public List<PostDto> getAll() {
        List<Post> posts = postRepository.getAll();
        return posts.stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Возвращает объект по id
     *
     * @param uuid id
     * @return объект клиента
     */
    public PostDto getById(UUID uuid) {
        Post post = postRepository.getById(uuid);
        return postMapper.toDto(post);
    }

    /**
     * Удаляет объект по id
     *
     * @param uuid id
     */
    public void delete(UUID uuid) {
        PostDto postDto = new PostDto();
        postDto.setId(uuid);
        Post post = postMapper.fromDto(postDto);
        postRepository.delete(post);
    }
}
