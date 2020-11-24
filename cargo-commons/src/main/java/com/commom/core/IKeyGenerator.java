package com.commom.core;

public interface IKeyGenerator<K> {

    /** key分隔字符 */
    public static final String KEY_SEPERATOR = "_";

    /**
     * 自定义key构造过程
     */
    K generate(K key);

}
