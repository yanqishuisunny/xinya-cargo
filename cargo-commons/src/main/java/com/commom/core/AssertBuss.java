package com.commom.core;

import com.commom.exception.BussException;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * @author 梁建军
 * 创建日期： 2019/8/7
 * 创建时间： 13:41
 * @version 1.0
 * @since 1.0
 */
public class AssertBuss {

    public static void notNull(@Nullable Object object, IBusCode busCode) {
        if (object == null) {
            throw new BussException(busCode);
        }
    }

    public static void notNull(@Nullable Object object) {
        if (object == null) {
            throw new BussException(BusCode.FAIL);
        }
    }

    public static void isNull(Object object, BusCode busCode) {
        if (object != null) {
            throw new BussException(busCode);
        }
    }

    /**
     * 大于零
     */
    public static void gtZero(@Nullable Long object, IBusCode busCode) {
        if (object == null || object <= 0) {
            throw new BussException(busCode);
        }
    }

    /**
     * 大于零
     */
    public static void gtZero(@Nullable Integer object, IBusCode busCode) {
        if (object == null || object <= 0) {
            throw new BussException(busCode);
        }
    }

    /**
     * 大于等于零
     */
    public static void geZero(@Nullable Long object, IBusCode busCode) {
        if (object == null || object < 0) {
            throw new BussException(busCode);
        }
    }

    /**
     * 大于等于零
     */
    public static void geZero(@Nullable Integer object, IBusCode busCode) {
        if (object == null || object < 0) {
            throw new BussException(busCode);
        }
    }

    /**
     * 小于零
     */
    public static void ltZero(@Nullable Long object, IBusCode busCode) {
        if (object == null || object >= 0) {
            throw new BussException(busCode);
        }
    }

    /**
     * 小于零
     */
    public static void ltZero(@Nullable Integer object, IBusCode busCode) {
        if (object == null || object >= 0) {
            throw new BussException(busCode);
        }
    }

    /**
     * 小于等于零
     */
    public static void leZero(@Nullable Long object, IBusCode busCode) {
        if (object == null || object > 0) {
            throw new BussException(busCode);
        }
    }

    /**
     * 小于等于零
     */
    public static void leZero(@Nullable Integer object, IBusCode busCode) {
        if (object == null || object > 0) {
            throw new BussException(busCode);
        }
    }


    public static void isTrue(boolean condition, IBusCode busCode) {
        if (!condition) {
            throw new BussException(busCode);
        }
    }

    public static void isFalse(boolean condition, IBusCode busCode) {
        if (condition) {
            throw new BussException(busCode);
        }
    }

    public static void notEmpty(@Nullable Object[] array, IBusCode busCode) {
        if (ObjectUtils.isEmpty(array)) {
            throw new BussException(busCode);
        }
    }

    public static void notEmpty(@Nullable Object o, IBusCode busCode) {
        if (ObjectUtils.isEmpty(o)) {
            throw new BussException(busCode);
        }
    }

    public static void isEmpty(@Nullable Object[] array, IBusCode busCode) {
        if (!ObjectUtils.isEmpty(array)) {
            throw new BussException(busCode);
        }
    }

    public static void isEmpty(@Nullable Object o, IBusCode busCode) {
        if (!ObjectUtils.isEmpty(o)) {
            throw new BussException(busCode);
        }
    }
}
