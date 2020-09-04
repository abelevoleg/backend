package vkudryashov.webserver.model;

import org.springframework.http.HttpStatus;

public class Error {
    private String statusCode;
    private String exceptionMessage;
    private String errorMessage;
    private String reasonPhrase;

    public Error(String statusCode, String exceptionMessage, String errorMessage) {
        this.statusCode = statusCode;
        this.exceptionMessage = exceptionMessage;
        this.errorMessage = errorMessage;
        try {
            this.reasonPhrase = HttpStatus.resolve(Integer.parseInt(statusCode)).getReasonPhrase();
        } catch (NullPointerException ex){
            this.reasonPhrase = null;
        }
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
