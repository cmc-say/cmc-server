package cmc.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    ACCESS_DENIED(403, "C006", "Access is Denied"),

    // User
    USER_NOT_FOUND(404, "U001", "해당 유저가 존재하지 않습니다."),
    DUPLICATED_BLOCK(400,"U002" , "중복된 차단입니다."),
    DUPLICATED_REPORT(400, "U003", "중복된 신고입니다."),
    SELF_BLOCK_OR_REPORT(400, "U004", "신고/차단하는 유저와 당하는 유저가 같습니다."),
    REPORT_TYPE_ERROR(400, "U005", "신고 타입이 잘못되었습니다."),

    // Jwt
    TOKEN_INVALID_EXCEPTION(401, "J001", "유효하지 않은 토큰입니다."),

    // s3
    FILE_SIZE_EXCEED(400, "S001" , "파일이 제한 크기를 초과하였습니다."),
    FILE_UPLOAD_ERROR(400, "S002", "파일 업로드에 실패하였습니다."),

    // world
    ORDER_TYPE_ERROR(400, "W001" , "정렬 타입이 잘못되었습니다." ),
    WORLD_NOT_FOUND(400, "W002", "존재하지 않는 세계관입니다."),
    WORLD_USER_LIMIT_ERROR(400, "W003", "설정하려는 user limit 가 현재의 세계관 인원보다 작습니다."),
    WORLD_NOT_HOST_ERROR(400, "W004", "방장 유저가 아닌 유저는 접근 권한이 없습니다"),

    // avatar
    AVATAR_NOT_FOUND(400, "A001", "해당 아이디의 캐릭터가 존재하지 않습니다." ),
    WORLD_AVATAR_NOT_FOUND(400, "A002", "해당 캐릭터는 해당 세계관에 참여하고 있지 않습니다."),
    DUPLICATED_AVATAR_WORLD_ENTER(400, "A003", "해당 캐릭터는 이미 세계관에 참여하고 있습니다."),

    // todo
    TODO_NOT_FOUND(400, "T001", "해당 todo 가 존재하지 않습니다."),
    TODO_CHECKED_NOT_FOUND(400, "T002", "체크되어 있지 않은 todo 입니다."),
    DUPLICATED_TODO_CHECK(400, "T003", "이미 체크되어 있는 todo 입니다."),

    // wordtoday
    WORDTODAY_NOT_FOUND(400, "WT001", "존재하지 않는 오늘의 한마디입니다." ),
    WORDTODAY_DUPLICATED(400, "WT002", "오늘 날짜의 오늘의 한마디가 이미 존재합니다."),

    // auth
    SOCIAL_TYPE_ERROR(400, "A001", "존재하지 않는 social 로그인 타입입니다."),
    SOCIAL_ACCESS_TOKEN_NOT_FOUND(400, "A002", "소셜 access token를 가져오는데 실패하였습니다."),
    SOCIAL_ID_NOT_FOUND(400, "A003", "소셜 access token를 통해 소셜 id를 가져오는데 실패하였습니다."),
    SOCIAL_AUTHORIZATTION_CODE_NOT_VALID(400, "A004", "유효하지 않은 소셜 로그인 인가코드 입니다."),
    COOKIE_REFRESH_TOKEN_NOT_FOUND(400, "A005", "쿠기에 리프래시 토큰이 존재하지 않습니다."),
    DB_REFRESH_TOKEN_NOT_FOUND(400, "A006", "디비에 해당 리프래시 토큰이 존재하지 않습니다."),
    REFRESH_TOKEN_NOT_VALID(400, "A007", "리프래시 토큰이 만료되었습니다."),
    // fcm
    FCM_ALARM_FAILED(400, "F001", "알림을 보내는데 실패하였습니다."),
    MAKE_FCM_MESSAGE_FAILED(400, "F002", "FCM 전송 메세지를 만드는데 실패하였습니다."),
    FCM_ACCESS_TOKEN_FAILED(400, "F003", "FCM access token 생성에 실패하였습니다."),
    FCM_REQUEST_FAILED(400, "F004", "FCM request 과정에 오류가 발생하였습니다."),
    RECOMMENDED_WORLD_NOT_FOUND(400, "R001", "추천 세계관이 존재하지 않습니다."),
    USER_DUPLICATED(400,"데모데이용 에러~ 방울 버거 바보" , "중복된 유저 이름입니다.");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }


}
