package config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Свойства приложения
 */
@AllArgsConstructor
@Data
public class AppProperties implements TestFileNameProvider {
    private String testFileName;
}
