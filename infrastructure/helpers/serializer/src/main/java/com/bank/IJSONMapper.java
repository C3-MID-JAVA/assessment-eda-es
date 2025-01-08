package com.bank;

public interface IJSONMapper {
    String writeToJson(Object obj);
    Object readFromJson(String json, Class<?> clazz);
}
