package cmc.domain.model;

import cmc.error.exception.BusinessException;
import cmc.error.exception.ErrorCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderType {

    RECENT("recent"), ID("id");

    private String orderTypeName;

    OrderType(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public static OrderType fromString(String orderTypeName) {
        return Arrays.stream(OrderType.values())
                .filter(o -> o.orderTypeName.equals(orderTypeName))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_TYPE_ERROR));
    }
}
