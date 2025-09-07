package es.us.dp1.l2_05_24_25.fantasy_realms.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceNotOwnedException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> resourceNotOwnedException(ResourceNotOwnedException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

//	@ExceptionHandler(value = TokenRefreshException.class)
//	@ResponseStatus(HttpStatus.FORBIDDEN)
//	public ResponseEntity<ErrorMessage> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
//		ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN.value(), new Date(), ex.getMessage(),
//				request.getDescription(false));
//
//		return new ResponseEntity<ErrorMessage>(message, HttpStatus.FORBIDDEN);
//	}	
	

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public final ResponseEntity<ErrorMessage> handleMethodArgumentException(MethodArgumentNotValidException ex,
			WebRequest request) {
		Map<String, Object> fieldError = new HashMap<>();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		fieldErrors.stream().forEach(error -> fieldError.put(error.getField(), error.getDefaultMessage()));
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), fieldError.toString(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN.value(), new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
	}

	// Manejar PlayerAlreadyInMatchException

    @ExceptionHandler(PlayerAlreadyInMatchException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> handlePlayerAlreadyInMatchException(PlayerAlreadyInMatchException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT.value(), new Date(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

	// Manejar MatchStatesException

    @ExceptionHandler(MatchStatesException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> MatchStatesException(MatchStatesException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT.value(), new Date(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

	// Manejar PlayerHasNotPrivileges
	
    @ExceptionHandler(PlayerHasNotPrivileges.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessage> PlayerHasNotPrivileges(PlayerHasNotPrivileges ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN.value(), new Date(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

	// Manejar ModStatesException
	
    @ExceptionHandler(ModStatesException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> ModStatesException(ModStatesException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT.value(), new Date(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

	// Manejar PlayerStatesException
	
    @ExceptionHandler(PlayerStatesException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> PlayerStatesException(PlayerStatesException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT.value(), new Date(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

	// Manejar TurnStatesException
	
    @ExceptionHandler(TurnStatesException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> TurnStatesException(TurnStatesException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT.value(), new Date(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

	// Manejar CardStatesException
	
    @ExceptionHandler(CardStatesException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> CardStatesException(CardStatesException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT.value(), new Date(), ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    	// Manejar InvalidStatusException
	
        @ExceptionHandler(InvalidStatusException.class)
        @ResponseStatus(value = HttpStatus.BAD_REQUEST)
        public ResponseEntity<ErrorMessage> InvalidStatusException(InvalidStatusException ex, WebRequest request) {
            ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
                    request.getDescription(false));
    
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        // Manejar InvalidCardTypeException
	
        @ExceptionHandler(InvalidStatesException.class)
        @ResponseStatus(value = HttpStatus.BAD_REQUEST)
        public ResponseEntity<ErrorMessage> InvalidCardTypeException(InvalidStatesException ex, WebRequest request) {
            ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
                    request.getDescription(false));
    
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

}
