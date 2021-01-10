package apgrison.codingtask.service;

import apgrison.codingtask.exception.ClientException;
import apgrison.codingtask.exception.InternalException;
import apgrison.codingtask.exception.NotFoundException;
import apgrison.codingtask.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResourceServiceImpl implements ResourceService {

    Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpHeaders headers;

    @Override
    public Object getAll(Resource resource) {
        try {
            return restTemplate.getForObject(resource.getEndpoint(), resource.getType());
        } catch (RuntimeException e) {
            return handleRestTemplateError(e);
        }
    }

    @Override
    public Object get(Resource resource, Integer id) {
        try {
            return restTemplate.getForObject(resource.getEndpoint() + "/" + id, resource.getType());
        } catch (RuntimeException e) {
            return handleRestTemplateError(e);
        }
    }

    @Override
    public Object create(Resource resource, Object object) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(object, headers);
            return restTemplate.postForObject(resource.getEndpoint(), entity, resource.getType());
        } catch (RuntimeException e) {
            return handleRestTemplateError(e);
        }
    }

    @Override
    public Object replace(Resource resource, Object object, Integer id) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(object, headers);
            ResponseEntity<?> responseEntity = restTemplate.exchange(resource.getEndpoint() + "/" + id, HttpMethod.PUT, entity, resource.getType());
            return responseEntity.getBody();
        } catch (RuntimeException e) {
            return handleRestTemplateError(e);
        }
    }

    @Override
    public Object patch(Resource resource, Object object, Integer id) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(object, headers);
            return restTemplate.patchForObject(resource.getEndpoint() + "/" + id, entity, resource.getType());
        } catch (RuntimeException e) {
            return handleRestTemplateError(e);
        }
    }

    @Override
    public Object delete(Resource resource, Integer id) {
        try {
            restTemplate.delete(resource.getEndpoint() + "/" + id);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (RuntimeException e) {
            return handleRestTemplateError(e);
        }
    }

    @Override
    public Object getAllNested(Resource resource, Integer id, Resource nested) {
        try {
            return restTemplate.getForObject(resource.getEndpoint() + "/" + id + "/" + nested.getPath(), nested.getType());
        } catch (RuntimeException e) {
            return handleRestTemplateError(e);
        }
    }

    @Override
    public Object createNested(Resource resource, Integer id, Resource nested, Object object) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(object, headers);
            return restTemplate.postForObject(resource.getEndpoint() + "/" + id + "/" + nested.getPath(), entity, nested.getType());
        } catch (RuntimeException e) {
            return handleRestTemplateError(e);
        }
    }

    public ResponseEntity<?> handleRestTemplateError(RuntimeException exception) {

        log.debug("Caught exception was {}", exception.getClass().getSimpleName());

        if (exception instanceof NotFoundException) {
            log.debug("returning {}", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);

        } else if (exception instanceof ClientException) {
            log.debug("returning {}", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);

        } else if (exception instanceof InternalException) {
            log.debug("returning {}", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);

        } else if (exception instanceof NullPointerException) {
            log.debug("the Resource was not resolved");
            log.debug("returning {}", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }
        return null;
    }

}
