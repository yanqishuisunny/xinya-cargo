package com.cargo.menedata.commom;

/**
 * @author Carlos
 */

public enum PictureEnum {

    /**
     * 人员从业资格证
     */
    qualificationUrl(0, "人员从业资格证"),
    /**
     * 身份证正面
     */
    idCardPos(10, "身份证正面"),
    /**
     * 身份证反面
     */
    idCardVer(20, "身份证反面"),
    /**
     * 驾驶证照片
     */
    driverLicenseUrl(30, "驾驶证照片");






    private final int type;
    private final String name;

    PictureEnum(Integer type, String name) {
        this.type = type;

        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static PictureEnum get(int type){
        for (PictureEnum value : values()) {
            if (value.getType() == type) {
                return value;
            }
        }
        return null;
    }


}
