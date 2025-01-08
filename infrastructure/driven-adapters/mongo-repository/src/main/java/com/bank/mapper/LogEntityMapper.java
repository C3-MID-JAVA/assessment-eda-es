package com.bank.mapper;

import com.bank.Log;
import com.bank.data.LogEntity;


public class LogEntityMapper {
    public static LogEntity toLogEntity(Log log) {
        LogEntity entity = new LogEntity();

        entity.setId(log.getId());
        entity.setMessage(log.getMessage());
        entity.setDateTime(log.getDateTime());

        return entity;
    }

    public static Log toLog(LogEntity entity) {
        Log log = new Log();

        log.setId(entity.getId());
        log.setMessage(entity.getMessage());
        log.setDateTime(entity.getDateTime());

        return log;
    }
}
