package cmc.service;

import cmc.domain.Avatar;
import cmc.domain.Todo;
import cmc.domain.World;
import cmc.repository.*;
import cmc.utils.S3Util;
import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@EnableJpaRepositories(basePackages = "cmc")
@EntityScan(basePackages = "cmc")
@Import({S3Util.class, AmazonS3Client.class, AvatarService.class})
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AvatarServiceTest {
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private AvatarRepository avatarRepository;
    @Autowired
    private WorldRepository worldRepository;
    @Autowired
    private CheckedTodoRepository checkedTodoRepository;

    @BeforeAll
    void init() {
        Avatar avatar = Avatar.builder()
                .avatarId(1L)
                .avatarName("아바타")
                .build();
        World world = World.builder()
                .worldId(1L)
                .worldName("세계관")
                .worldUserLimit(10)
                .worldStartDate(LocalDateTime.now())
                .worldEndDate(LocalDateTime.now())
                .build();
        Todo todo = Todo.builder()
                .todoId(1L)
                .todoContent("임시")
                        .world(world)
                                .build();
        world.getTodos().add(todo);

        worldRepository.save(world);
        avatarRepository.save(avatar);
    }

    @Test
    void checkTodo() {
        avatarService.checkTodo(1L, 1L, 1L);
//        System.out.println(worldRepository.findAll());
//        System.out.println(todoRepository.findAll());
        System.out.println(avatarRepository.findAll());
        System.out.println(checkedTodoRepository.findAll());
    }

//    @Test
//    void testDb() {
//        try(Connection connection = dataSource.getConnection()) {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}