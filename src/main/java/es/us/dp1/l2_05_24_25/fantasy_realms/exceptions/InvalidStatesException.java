package es.us.dp1.l2_05_24_25.fantasy_realms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class InvalidStatesException extends RuntimeException {
	

	public InvalidStatesException(String message) {
		super(message);
	}

}
