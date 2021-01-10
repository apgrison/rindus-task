package apgrison.codingtask.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Handle errors resolved by the DefaultHandlerExceptionResolver:
 * For example:
 *   if a request body required by the controller is missing (400 BAD_REQUEST)
 *   if a request method is not supported by the request mapping (405 METHOD_NOT_ALLOWED)
 */
@RestController
public class ResourceErrorController implements ErrorController {

    Logger log = LoggerFactory.getLogger(ResourceErrorController.class);

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        log.debug("error handled by the {}", this.getClass().getSimpleName());

        Object errorCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (errorCode != null) {
            int statusCode = Integer.parseInt(errorCode.toString());
            HttpStatus status = HttpStatus.resolve(statusCode);
            if (status == null) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            ResponseEntity<String> responseEntity = new ResponseEntity<>("{}", status);

            log.debug("returning {}", status);
            return responseEntity;
        }

        return null;
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
