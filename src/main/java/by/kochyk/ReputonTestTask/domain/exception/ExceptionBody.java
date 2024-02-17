package by.kochyk.ReputonTestTask.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionBody {

    private int status;
    private String message;
}
