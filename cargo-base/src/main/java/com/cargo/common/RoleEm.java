package com.cargo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

public interface RoleEm {
    /**
     * 普通用户
     */
    int NORMAL_USER = 1;

    /**
     * 企业管理员
     */
    int ORG_ADMIN = 2;

    /**
     * 超级管理员
     */
    int SUPER_ADMIN = 3;

    @Getter
    @AllArgsConstructor
    enum PermType {
        /**
         * 菜单权限
         */
        MENU("1", "菜单权限"),
        /**
         * 按钮（操作权限）
         */
        OPTS("0", "操作权限"),
        /**
         * 按钮与菜单混合权限
         */
        MENUOPT("2", "混合权限");

        private final String code;
        private final String message;
    }

    @Getter
    @AllArgsConstructor
    enum DefaultRole {
        /**
         * 车主
         */
        PERSON_VEHICLE_OWNER("001", "车主"),
        /**
         * 司机
         */
        DRIVER("002", "司机"),
        /**
         * 货主
         */
        SHIPPER("003", "货主"),
        /**
         * 押运员
         */
        PERSON_GOODS_OWNER("013", "押运员"),

        /**
         * 装卸工
         */
        ORG_GOODS_OWNER("014", "装卸工"),

        /**
         * 运营商
         */
        ORG_ISP("006", "运营商"),
        /**
         *
         */
        ORG_CALL("007", "客服"),

        /**
         * 生产商
         */
        ORG_PRODUCT("1","生产商"),

        /**
         * 贸易商
         */
        ORG_TRADE("2","贸易商"),

        /**
         * 承运商 （企业车主）
         */
        ORG_VEHICLE_OWNER("8", "承运商"),

        /**
         * 仓储商
         */
        ORG_STORAGE("4","仓储商")

        ;

        /**
         * 方法描述: 枚举转换
         *
         * @param code code
         * @return BusCode BusCode
         */
        public static DefaultRole parseOf(String code) {
            for (DefaultRole item : values()) {
                if (Objects.equals(item.getCode(), code)) {
                    return item;
                }
            }
            return null;
        }

        private final String code;
        private final String name;
    }


}


