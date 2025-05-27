package ru.otus.project.controllers.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.dto.models.ToolDto;
import ru.otus.project.services.ToolsService;

import java.util.List;

@RestController
@RequestMapping("/tools")
@RequiredArgsConstructor
public class ToolsRestController {

    private final ToolsService toolsService;

    // Получение всех инструментов
    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ToolDto> getAllTools() {
        return toolsService.findAll();
    }

    // Получение инструмента по ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ToolDto getToolById(@PathVariable Long id) {
        return toolsService.findById(id);
    }

    // Создание нового инструмента
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ToolDto createTool(@RequestBody ToolDto toolDto) {
        return toolsService.createUpdate(toolDto);
    }

    // Обновление инструмента
    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ToolDto updateTool(@PathVariable Long id, @RequestBody ToolDto toolDto) {
        toolDto.setId(id);
        return toolsService.createUpdate(toolDto);
    }

    // Удаление инструмента по ID
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public List<ToolDto> deleteTool(@PathVariable Long id) {
        toolsService.deleteById(id);
        return toolsService.findAll();
    }

}
