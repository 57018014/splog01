package com.yy.common;

import com.alibaba.druid.support.json.JSONUtils;
import com.yy.excption.code.ErrorCode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.yy.excption.code.ErrorCode.SUCCESS;


/**
 * @author yaoyuan
 * Created on 2019/12/04
 */
public class RestResult implements Map<String, Object> {

    public static final String RESULT = "result";
    public static final String ERROR_MSG = "error_msg";
    private static final String HOST_NAME = "hostName";

    private final Map<String, Object> inner = new HashMap<>();

    public static RestResult success() {
        return new RestResult(SUCCESS.getErrorCode());
    }

    public static RestResult fail(ErrorCode errorCode) {
        return fail(errorCode.getErrorCode(), errorCode.getErrorMsg());
    }

    /**
     * @param code
     * @param message
     * @return
     */
    public static RestResult fail(int code, String message) {
        RestResult result = new RestResult(code);
        if (message != null) {
            result.put(ERROR_MSG, message);
        }
        return result;
    }

    private RestResult(int code) {
        put(RESULT, code);
        put(HOST_NAME, HostInfo.getHostName());
    }

    @Override
    public void clear() {
        inner.clear();
    }

    @Override
    public Set<String> keySet() {
        return inner.keySet();
    }

    @Override
    public Collection<Object> values() {
        return inner.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return inner.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return inner.equals(o);
    }

    @Override
    public int hashCode() {
        return inner.hashCode();
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        return inner.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Object> action) {
        inner.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super Object, ?> function) {
        inner.replaceAll(function);
    }

    @Override
    public Object putIfAbsent(String key, Object value) {
        return inner.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return inner.remove(key, value);
    }

    @Override
    public boolean replace(String key, Object oldValue, Object newValue) {
        return inner.replace(key, oldValue, newValue);
    }

    @Override
    public Object replace(String key, Object value) {
        return inner.replace(key, value);
    }

    @Override
    public Object computeIfAbsent(String key, Function<? super String, ?> mappingFunction) {
        return inner.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Object computeIfPresent(String key,
                                   BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return inner.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Object compute(String key,
                          BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return inner.compute(key, remappingFunction);
    }

    @Override
    public Object merge(String key, Object value,
                        BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return inner.merge(key, value, remappingFunction);
    }

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public boolean isEmpty() {
        return inner.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return inner.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return inner.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return inner.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return inner.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return inner.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        inner.putAll(m);
    }

    @Override
    public String toString() {
        return JSONUtils.toJSONString(this);
    }
}
