package cmc.service;

import cmc.domain.World;
import cmc.repository.WorldHashtagRepository;
import cmc.repository.WorldRepository;
import cmc.utils.S3Util;
import com.amazonaws.services.s3.AmazonS3Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@ExtendWith(SpringExtension.class)
@EnableJpaRepositories(basePackages = "cmc")
@EntityScan(basePackages = "cmc")
@Import({S3Util.class, AmazonS3Client.class, WorldService.class})
@DataJpaTest
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WorldServiceTest {
    @Autowired
    private WorldService worldService;
    @Autowired
    private WorldRepository worldRepository;
    @Autowired
    private WorldHashtagRepository worldHashtagRepository;
    @Autowired
    private EntityManager em;

    @RepeatedTest(1000)
    void saveWorld() throws IOException {
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/testImage/testImage.png"));
        worldService.saveWorld(1L,
                file,
                "worldName",
                "worldNotice",
                10,
                "worldPassword",
                List.of("태그1", "태그2", "태그3"),
                List.of("todo1", "todo2", "todo3"),
                null
                );
        List<World> world = worldRepository.findAll();
        Assertions.assertThat(world).isNotNull();
    }

    @RepeatedTest(1000)
    void saveWorldRefactor() throws IOException {
        MockMultipartFile file = new MockMultipartFile("image",
                "test.png",
                "image/png",
                new FileInputStream("src/test/resources/testImage/testImage.png"));
        worldService.refactor_saveWorld(1L,
                file,
                "worldName",
                "worldNotice",
                10,
                "worldPassword",
                List.of("태그1", "태그2", "태그3"),
                List.of("todo1", "todo2", "todo3"),
                null
        );
        List<World> world = worldRepository.findAll();
        Assertions.assertThat(world).isNotNull();
    }
}