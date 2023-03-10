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
    WORLD_IMG_UPDATED(HttpStatus.CREATED, "세계관 이미지 수정에 성공하였습니다."),
    WORLD_HASHTAG_CREATED(HttpStatus.CREATED, "세계관 해시태그 수정 (추가) 에 성공하였습니다."),
    WORLD_HASHTAG_DELETED(HttpStatus.OK, "세계관 해시태그 수정 (삭제) 에 성공하였습니다."),
    HASHTAG_IN_ORDER_FOUND(HttpStatus.OK, "해시태그 정렬로 조회에 성공하였습니다."),
    WORLD_TODO_TODAY_FOUND(HttpStatus.OK, "세계관 캐릭터 전체의 체크리스트 달성 현황 조회"),

    // avatar
    AVATAR_IMG_UPDATED(HttpStatus.CREATED, "캐릭터 이미지가 업데이트 되었습니다."),
    AVATAR_INFO_UPDATED(HttpStatus.OK, "캐릭터 정보가 업데이트 되었습니다."),
    AVATAR_FOUND(HttpStatus.OK, "아바타 조회에 성공하였습니다."),
    AVATAR_DELETED(HttpStatus.OK, "아바타가 삭제되었습니다."),
    AVATAR_ENTER_WORLD(HttpStatus.CREATED, "아바타가 세계관에 참여했습니다."),
    AVATAR_QUIT_WORLD(HttpStatus.OK, "아바타가 세계관 탈퇴에 성공하였습니다."),

    // todo
    TODO_CHECKED(HttpStatus.CREATED, "todo 체크에 성공했습니다."),
    TODO_UNCHECKED(HttpStatus.OK, "todo 체크 해제에 성공했습니다."),
    WORDTODAY_CREATED(HttpStatus.CREATED, "오늘의 한마디 생성에 성공하였습니다."),
    AVATAR_CHECKED_TODO_TODAY_FOUND(HttpStatus.OK, "캐릭터가 속해있는 특정 세계관에서 오늘 체크한 todo 리스트 조회에 성공했습니다."),
    AVATAR_CHECKED_TODO_FOR_MONTH_FOUND(HttpStatus.OK, "캐릭터가 갖고 있는 세계관들의 한달동안 체크된 todo 리스트 조회에 성공했습니다."),
    AVATAR_IN_WORLD_WITHOUT_BLOCKED_USER_FOUND(HttpStatus.OK, "차단한 유저를 제외한 세계관 속 캐릭터 리스트 조회에 성공했습니다."),
    WORLD_WORD_TODAY_FOUND(HttpStatus.OK, "차단한 유저를 포함한 세계관 속 캐릭터들의 오늘 날짜의 오늘의 한마디 조회에 성공했습니다."),
    // 추천 목록
    RECOMMENDED_WORLD_FOUND(HttpStatus.OK, "세계관 추천 목록 조회에 성공하였습니다."),
    RECOMMENDED_TODO_FOUND(HttpStatus.OK, "todo 추천 목록 조회에 성공하였습니다.");

    private final HttpStatus status;
    private final String message;

}