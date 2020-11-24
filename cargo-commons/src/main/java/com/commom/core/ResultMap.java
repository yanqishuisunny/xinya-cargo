package com.commom.core;

import java.util.HashMap;
import java.util.Map;

public class ResultMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = 696427811135472911L;

    /**
     * key生成器对象
     */
    private IKeyGenerator<K> generator;

    /**
     * 构造器
     */
    public ResultMap(IKeyGenerator<K> generator) {
        super();
        this.generator = generator;
    }

    /**
     * 设置value绑定到key
     */
    @Override
    public V put(K key, V value) {
        /*if (key != null) {
            return super.put(generator.generate(key), value);
        } else {
            return super.put(key, value);
        }*/
        return super.put(generator.generate(key), value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
    	/*if (key != null) {
            return super.get(generator.generate((K)key));
        } else {
            return super.get(key);
        }*/
        return super.get(generator.generate((K)key));
    }

    /**
     * 设置map中的values绑定到keys
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

}

