package apgrison.codingtask.controller;

import apgrison.codingtask.exception.ClientException;
import apgrison.codingtask.exception.InternalException;
import apgrison.codingtask.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;

/**
 * Throw custom exceptions when intercepting http responses returned by the rest template
 */
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    Logger log = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return (
                httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        log.debug("error handled by the {}", this.getClass().getSimpleName());

        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            throw new InternalException();
        } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException();
            }
            throw new ClientException();
        }
    }

}
