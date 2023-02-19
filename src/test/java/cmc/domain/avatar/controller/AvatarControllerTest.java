package cmc.domain.avatar.controller;

import cmc.TestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class AvatarControllerTest extends TestSupport {

    @DisplayName("유저가 갖고 있는 캐릭터 조회")
    @Test
    void getCharacters() throws Exception{
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}