package cmc.common;

import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private final int statusCode;
    private final String message;
    private final T data;

    public ResponseDto(ResponseCode responseCode, T data) {
        this.statusCode = responseCode.getStatus().value();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    public ResponseDto(ResponseCode responseCode) {
        this.statusCode = responseCode.getStatus().value();
        this.message = responseCode.getMessage();
        this.data = null;
    }
}