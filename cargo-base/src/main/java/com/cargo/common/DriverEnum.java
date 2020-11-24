package com.cargo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关于司机的枚举
 * @author 赵恒亮
 */

public interface DriverEnum {
    @Getter
    @AllArgsConstructor
    enum DriverStatus{
        /**
         * 空闲
         */
        DRIVER_STATUS_LEISURE(0,"空闲"),
        /**
         * 出车中
         */
        DRIVER_STATUS_WORK(1,"出车中"),
        /**
         * 请假
         */
        DRIVER_STATUS_LEAVE(2,"请假");
        private Integer integer;
        private String status;

        public static DriverStatus parseOf(int code) {
            for (DriverStatus item : values()) {
                if (item.getInteger() == code) {
                    return item;
                }
            }
            return null;
        }

    }
}
