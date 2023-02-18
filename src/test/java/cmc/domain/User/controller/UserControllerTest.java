package cmc.domain.User.controller;

import cmc.TestSupport;
import cmc.domain.User.entity.User;
import cmc.domain.User.model.SocialType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class UserControllerTest extends TestSupport {

//    @DisplayName("user/characters")
//    @Test
//    void getCharacters() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders.get("api/v1/user/characters"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(print());
//    }

    @DisplayName("유저 회원 탈퇴 테스트")
    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

//    @Test
//    void isMemberOfWorld() {
//    }

    @DisplayName("유저 신고 테스트")
    @Test
    void reportUser() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/user/1/report"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @DisplayName("유저 차단 테스트")
    @Test
    void blockUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/user/1/block"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}