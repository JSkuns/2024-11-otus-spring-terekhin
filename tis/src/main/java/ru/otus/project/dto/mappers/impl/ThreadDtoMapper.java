package ru.otus.project.dto.mappers.impl;

import org.springframework.stereotype.Component;
import ru.otus.project.dto.mappers.DtoMapper;
import ru.otus.project.dto.models.ThreadDto;
import ru.otus.project.models.Thread;

@Component
public class ThreadDtoMapper implements DtoMapper<ThreadDto, Thread> {

    @Override
    public ThreadDto toDto(Thread thread) {
        if (thread == null) {
            return null;
        }
        return ThreadDto.builder()
                .id(thread.getId())
                .threadDiameter(thread.getThreadDiameter())
                .pitch(thread.getPitch())
                .toleranceClass(thread.getToleranceClass())
                .build();
    }

    @Override
    public Thread toModel(ThreadDto dto) {
        if (dto == null) {
            return null;
        }
        Thread thread = new Thread();
        thread.setId(dto.getId());
        thread.setThreadDiameter(dto.getThreadDiameter());
        thread.setPitch(dto.getPitch());
        thread.setToleranceClass(dto.getToleranceClass());
        return thread;
    }

}