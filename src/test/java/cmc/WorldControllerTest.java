package cmc;

import cmc.TestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorldControllerTest extends TestSupport {

//    @Test
//    void saveWorld() {
//    }

//    @Test
//    void getWorldsByAvatar() {
//    }

    @DisplayName("세계관에 참여하고 있는 유저인지 조회")
    @Test
    void isMemberOfWorld() throws Exception {
        // given

        // when
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/user/world/{worldId}/isMember", 3)
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

//
//    @Test
//    void getWorldWithOrderRecent() {
//    }
//
//    @Test
//    void deleteWorld() {
//    }
//
//    @Test
//    void updateWorldInfo() {
//    }
//
//    @Test
//    void updateWorldImg() {
//    }
//
//    @Test
//    void getWorld() {
//    }
//
//    @Test
//    void searchWorld() {
//    }
//
//    @Test
//    void getRecommendedWorld() {
//    }
//
//    @Test
//    void getPopularHashtags() {
//    }
}