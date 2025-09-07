package es.us.dp1.l2_05_24_25.fantasy_realms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ModStatesException extends RuntimeException {
    public ModStatesException(String message) {
        super(message);
    }
}
