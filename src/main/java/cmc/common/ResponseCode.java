package cmc.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    // user
    USER_CREATED(HttpStatus.CREATED, "유저 회원가입에 성공하였습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "유저 로그인에 성공하였습니다."),
    USER_DELETE_SUCCESS(HttpStatus.OK, "유저 회원 탈퇴에 성공하였습니다."),
    USER_REPORT_SUCCESS(HttpStatus.OK, "유저 신고에 성공하였습니다."),
    USER_BLOCK_SUCCESS(HttpStatus.OK, "유저 차단에 성공하였습니다."),
    USER_CHARACTERS_FOUND(HttpStatus.OK, "유저의 캐릭터 조회에 성공하였습니다."),

    // avatar
    AVATAR_SAVE_SUCCESS(HttpStatus.OK, "아바타 저장에 성공하였습니다."),

    // world
    WORLD_SAVE_SUCCESS(HttpStatus.CREATED, "세계관 저장에 성공하였습니다."),
    WORLD_WITH_CHARACTER_FOUND_SUCCESS(HttpStatus.OK, "캐릭터가 가지고 있는 세계관 조회에 성공하였습니다."),
    USER_IS_MEMBER_OF_WORLD_FOUND(HttpStatus.OK, "유저 세계관 참여 여부 조회에 성공하였습니다." ),
    WORLD_FOUND_SUCCESS(HttpStatus.OK, "세계관 조회에 성공하였습니다."),
    WORLD_DELETED(HttpStatus.OK, "세계관 삭제에 성공하였습니다."),
    WORLD_SEARCH_SUCCESS(HttpStatus.OK, "세계관 검색에 성공하였습니다."),
    WORLD_INFO_UPDATED(HttpStatus.OK, "세계관 정보 수정에 성공하였습니다."),
    WORLD_IMG_UPDATED(HttpStatus.CREATED, "세계관 이미지 수정에 성공하였습니다.");

    private final HttpStatus status;
    private final String message;

}