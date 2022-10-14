package repository;

import configuration.H2JdbcConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.citros.documentflow.model.organizational_structure.Post;
import ru.citros.documentflow.repository.GeneralRepository;
import ru.citros.documentflow.repository.PostRepository;
import ru.citros.documentflow.repository_util.preparedstatementsetter.PostBatchPreparedStatementSetter;
import ru.citros.documentflow.repository_util.row_mapper.PostRowMapper;
import ru.citros.documentflow.service.converter.Converter;
import ru.citros.documentflow.service.converter.ConverterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {
        PostRepository.class,
        H2JdbcConfiguration.class,
        ConverterImpl.class,
        PostBatchPreparedStatementSetter.class,
        PostRowMapper.class
})
@ActiveProfiles("test")
public class PostRepositoryTest {

    @Autowired
    private GeneralRepository<Post> postRepository;

    @Test
    public void saveTest() {
        UUID uuidPost = UUID.randomUUID();
        String namePost = "Director";
        Post post = new Post(uuidPost, namePost);
        postRepository.save(post);
        Post persistsPost = postRepository.getById(uuidPost).get();
        assertEquals(post.getId(), persistsPost.getId());
    }

    @Test
    public void saveAllTest() {
        int currentSizePost = postRepository.getAll().size();

        UUID uuidPost1 = UUID.randomUUID();
        String namePost1 = "Director";
        Post post1 = new Post(uuidPost1, namePost1);

        UUID uuidPost2 = UUID.randomUUID();
        String namePost2 = "Director";
        Post post2 = new Post(uuidPost2, namePost2);

        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        postRepository.saveAll(posts);

        int newSizePost = postRepository.getAll().size();
        assertEquals(currentSizePost + 2, newSizePost);
    }

    @Test
    public void updateTest() {
        List<Post> posts = postRepository.getAll();
        Post currentPost = posts.get(0);
        UUID postId = currentPost.getId();
        String currentName = currentPost.getName();
        currentPost.setName("Traktorist");
        postRepository.update(currentPost);
        Post updatedPost = postRepository.getById(postId).get();
        String updatedName = updatedPost.getName();
        assertNotEquals(currentName, updatedName);
    }

    @Test
    public void getAll() {
        UUID uuidPost = UUID.randomUUID();
        String namePost = "Director";
        Post post = new Post(uuidPost, namePost);
        postRepository.save(post);
        int sizePosts = postRepository.getAll().size();
        assertTrue(sizePosts > 0);
    }

    @Test
    public void getByIdTest() {
        UUID uuidPost = UUID.randomUUID();
        String namePost = "Director";
        Post post = new Post(uuidPost, namePost);
        postRepository.save(post);
        Post savedPost = postRepository.getById(uuidPost).get();
        UUID savedId = savedPost.getId();
        assertTrue(uuidPost.equals(savedId));
    }

    @Test
    public void deleteTest() {
        UUID uuidPost = UUID.randomUUID();
        String namePost = "Director";
        Post post = new Post(uuidPost, namePost);
        postRepository.save(post);
        int currentSizePost = postRepository.getAll().size();
        postRepository.delete(post);
        int sizePostsTableAfterDelete = postRepository.getAll().size();
        assertTrue((currentSizePost - sizePostsTableAfterDelete) == 1);
    }
}
