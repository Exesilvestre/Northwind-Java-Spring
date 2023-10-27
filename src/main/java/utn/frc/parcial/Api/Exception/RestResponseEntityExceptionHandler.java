package utn.frc.parcial.Api.Exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String EXCEPTION_HANDLED_BY = "(Rest)ResponseEntityExceptionHandler (@ControllerAdvice)";

    // ------------------------------------------------------------------------
    // Overriding métodos de clase base ya gestionados (mediante
    // @ExceptionHandler en dicha clase base)
    // ------------------------------------------------------------------------
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {

        List<String> errors = new ArrayList<String>();

        // for simple (ver abajo opción foreach + lambda)
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        // for simple (ver abajo opción foreach + lambda)
        for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiErrorEntity apiError = new ApiErrorEntity(
                EXCEPTION_HANDLED_BY,
                "MethodArgumentNotValidException (overriden)",
                statusCode,
                HttpStatus.BAD_REQUEST,
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                e.getLocalizedMessage(),
                errors
        );

        return handleExceptionInternal(e, apiError, headers, apiError.getStatus(), request);
        //return new ResponseEntity<Object>(apiError, headers, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException e,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {

        String error = e.getParameterName() + " parameter is missing";

        ApiErrorEntity apiError = new ApiErrorEntity(
                EXCEPTION_HANDLED_BY,
                "MissingServletRequestParameterException (overriden)",
                statusCode,
                HttpStatus.BAD_REQUEST,
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                e.getLocalizedMessage(),
                error
        );

        //return handleExceptionInternal(e, apiError, headers, apiError.getStatus(), request);
        return new ResponseEntity<Object>(apiError, headers, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {

        String error = "Request body inexistente o mal formado";

        ApiErrorEntity apiError = new ApiErrorEntity(
                EXCEPTION_HANDLED_BY,
                "HttpMessageNotReadableException (overriden)",
                statusCode,
                HttpStatus.BAD_REQUEST,
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                e.getLocalizedMessage(),
                error
        );

        //return handleExceptionInternal(e, apiError, headers, apiError.getStatus(), request);
        return new ResponseEntity<Object>(apiError, headers, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException e,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {

        String error = "No handler found for " + e.getHttpMethod() + " " + e.getRequestURL();

        ApiErrorEntity apiError = new ApiErrorEntity(
                EXCEPTION_HANDLED_BY,
                "NoHandlerFoundException (overriden)",
                statusCode,
                HttpStatus.NOT_FOUND,
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                e.getLocalizedMessage(),
                error
        );

        //return handleExceptionInternal(e, apiError, headers, apiError.getStatus(), request);
        return new ResponseEntity<Object>(apiError, headers, apiError.getStatus());
    }

    // ------------------------------------------------------------------------
    // Nuevos métodos con @ExceptionHandler para excepciones/errores custom y
    // default handler.
    // (ver alternativas WebRequest, ServletWebRequest, HttpServletRequest)
    // ------------------------------------------------------------------------
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFound(
            ResourceNotFoundException e,
            WebRequest request
    ) {

        String error = e.getMessage();

        ApiErrorEntity apiError = new ApiErrorEntity(
                EXCEPTION_HANDLED_BY,
                "(Custom)ResourceNotFoundException (@ExceptionHandler)",
                null,
                HttpStatus.NOT_FOUND,
                ((ServletWebRequest)request).getRequest().getRequestURI(),
                e.getLocalizedMessage(),
                error
        );

        //return handleExceptionInternal(e, apiError, new HttpHeaders(), apiError.getStatus(), request);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestException(
            BadRequestException e,
            ServletWebRequest request
    ) {

        String error = e.getMessage();

        ApiErrorEntity apiError = new ApiErrorEntity(
                EXCEPTION_HANDLED_BY,
                "(Custom)BadRequestException (@ExceptionHandler)",
                null,
                HttpStatus.BAD_REQUEST,
                request.getRequest().getRequestURI(),
                e.getLocalizedMessage(),
                error
        );

        //return handleExceptionInternal(e, apiError, new HttpHeaders(), apiError.getStatus(), request);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException e,
            HttpServletRequest request
    ) {

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<String> errors = new ArrayList<String>();

        // foreach + lambda (ver arriba opción for simple)
        if (!violations.isEmpty()) {
            violations.forEach(
                    violation -> errors.add(
                            violation.getRootBeanClass().getName() + " " +
                                    violation.getPropertyPath() + ": " + violation.getMessage()
                    )
            );
        }

        ApiErrorEntity apiError = new ApiErrorEntity(
                EXCEPTION_HANDLED_BY,
                "ConstraintViolationException (@ExceptionHandler)",
                null,
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                e.getLocalizedMessage(),
                errors
        );

        //return handleExceptionInternal(e, apiError, new HttpHeaders(), apiError.getStatus(), request);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e,
            HttpServletRequest request
    ) {

        String error =
                e.getName() + " should be of type " + e.getRequiredType().getName();

        ApiErrorEntity apiError = new ApiErrorEntity(
                EXCEPTION_HANDLED_BY,
                "MethodArgumentTypeMismatchException (@ExceptionHandler)",
                null,
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                e.getLocalizedMessage(),
                error
        );

        //return handleExceptionInternal(e, apiError, new HttpHeaders(), apiError.getStatus(), request);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // Default handler
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception e, HttpServletRequest request) {
        String error = "error occurred";

        ApiErrorEntity apiError = new ApiErrorEntity(
                EXCEPTION_HANDLED_BY,
                "Exception (@ExceptionHandler)",
                null,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI(),
                e.getLocalizedMessage(),
                error
        );

        //return handleExceptionInternal(e, apiError, new HttpHeaders(), apiError.getStatus(), request);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
