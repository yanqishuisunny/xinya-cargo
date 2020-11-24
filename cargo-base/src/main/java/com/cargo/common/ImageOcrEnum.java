package com.cargo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ocr 枚举类
 *
 * @author 赵恒亮
 */

@Getter
@AllArgsConstructor
public enum ImageOcrEnum {


    /**
     * 针对与 2101-图片OCR识别   是0-7
     *  方法名： iamgeOCR
     * 0表示身份证识别，1营业执照识别，2表示驾驶证识别，3行驶证识别,4交通局的车辆信息查询（返回车的好多信息），5企业的道路运输经营许可证，
     *   6危险化学品经营许可证,7百度-自定义模板（中华人民共和国道路运输证), 8-安全生产许可证
     */

    /**
     * 针对与 2102-信息检测 是 0-3
     * 方法名 ： msgCheck
     * 0身份信息认证，1企业信息认证，2企业法人股东高管核查,3驾驶证核查
     */

    IDENTITY("0"),

    BUSHINESS("1"),

    DRIVES("2"),

    TRAVEL("3"),

    RTA("4"),

    ROAD("5"),

    DANGER("6"),

    CUSTOM("7"),

    SAFETY("8");

    private String type;

    public static ImageOcrEnum getImge(String type) {
        for (ImageOcrEnum imageOcrEnum : ImageOcrEnum.values()) {
            if (imageOcrEnum.getType().equals(type)) {
                return imageOcrEnum;
            }
        }
        return null;
    }

}
