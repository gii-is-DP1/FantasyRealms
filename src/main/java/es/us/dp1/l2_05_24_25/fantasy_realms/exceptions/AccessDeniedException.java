package es.us.dp1.l2_05_24_25.fantasy_realms.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
@Getter
public class AccessDeniedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1461835347378078101L;

	public AccessDeniedException() {
		super("Access denied!");
	}
	
	public AccessDeniedException(String message) {
		super(message);
	}
	
}
