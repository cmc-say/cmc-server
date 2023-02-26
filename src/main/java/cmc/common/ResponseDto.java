package cmc.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ResponseDto<T> {
    @Schema(name = "status code")
    private final int statusCode;
    @Schema(name = "message")
    private final String message;
    @Schema(name = "data")
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