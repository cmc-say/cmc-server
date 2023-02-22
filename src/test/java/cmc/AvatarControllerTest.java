package cmc;

import cmc.TestSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AvatarControllerTest extends TestSupport {

    @DisplayName("유저가 갖고 있는 캐릭터 조회")
    @Test
    void getAvatars() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/user/avatars")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return "1";
                    }
                })
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("캐릭터 생성")
    @Test
    void saveAvatar() throws Exception{
        //Given
        final String fileName = "testImage"; //파일명
        final String contentType = "png"; //파일타입
        final String filePath = "src/test/resources/testImage/"+fileName+"."+contentType; //파일경로
        FileInputStream fileInputStream = new FileInputStream(filePath);

        //Mock파일생성
        MockMultipartFile image = new MockMultipartFile(
                "file",
                fileName+"."+contentType,
                MediaType.MULTIPART_FORM_DATA_VALUE,
                fileInputStream
        );

        MockMultipartFile data = new MockMultipartFile("data","", "application/json","{\"avatarName\":\"아바타이름\", \"avatarMessage\":\"아바타메세지\"}".getBytes());

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/api/v1/avatar")
                        .file(image)
                        .file(data)
                        .principal(new Principal() {
                            @Override
                            public String getName() {
                                return "1";
                            }
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}