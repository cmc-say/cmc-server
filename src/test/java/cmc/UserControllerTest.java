package cmc;

import cmc.dto.request.ReportUserRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class UserControllerTest extends TestSupport {

//    @DisplayName("user/characters")
//    @Test
//    void getCharacters() throws Exception{
//    }

    @DisplayName("유저 회원 탈퇴 테스트")
    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/user")
                        .principal(new Principal() {
                            @Override
                            public String getName() {
                                return "1";
                            }
                        })
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

//    @Test
//    void isMemberOfWorld() {
//    }

    @DisplayName("유저 신고 테스트")
    @Test
    void reportUser() throws Exception{
        // given
        String body = objectMapper.writeValueAsString(new ReportUserRequestDto("world"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/user/2/report")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(new Principal() {
                            @Override
                            public String getName() {
                                return "1";
                            }
                        })
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @DisplayName("유저 차단 테스트")
    @Test
    void blockUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/user/2/block")
                        .principal(new Principal() {
                            @Override
                            public String getName() {
                                return "1";
                            }
                        })
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}