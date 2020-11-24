package com.commom.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface Constant {

    String ZERO = "0";

    /**
     * 区域等级-省
     */
    String AREA_LEVEL_PROVINCE =  "4";
    /**
     * 区域等级-城市
     */
    String AREA_LEVEL_CITY =  "5";
    /**
     * 区域等级-区县
     */
    String AREA_LEVEL_CITYID =  "6";


    @Getter
    @AllArgsConstructor
    enum AbleEnum {

        YES(1, "有效"),
        NO(0, "无效");

        private final Integer value;

        private final String comment;

    }

    @Getter
    @AllArgsConstructor
    enum Status {

        ENABLE(1, "启用"),
        DISABLED(0, "禁用");

        private final Integer value;

        private final String comment;

    }

    @Getter
    @AllArgsConstructor
    enum IS {

        YES(1),
        NO(0);

        private final Integer value;
    }

    @Getter
    @AllArgsConstructor
    enum FlgHidden {

        ENABLE(1, "隐藏"),
        DISABLED(0, "不隐藏");

        private final Integer value;

        private final String comment;

    }
}
