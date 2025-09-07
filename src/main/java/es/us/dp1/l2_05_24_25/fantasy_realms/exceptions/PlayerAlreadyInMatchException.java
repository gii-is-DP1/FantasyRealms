package es.us.dp1.l2_05_24_25.fantasy_realms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class PlayerAlreadyInMatchException extends RuntimeException {
    public PlayerAlreadyInMatchException(String message) {
        super(message);
    }
}
