package apgrison.codingtask.controller;

import apgrison.codingtask.model.Resource;
import apgrison.codingtask.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    @GetMapping(value = "/{resource}", produces = JSON)
    public Object getResourceList(@PathVariable String resource) {
        return resourceService.getAll(Resource.getByPath(resource, true));
    }

    @GetMapping(value = "/{resource}/{id}", produces = JSON)
    public Object getResource(@PathVariable String resource, @PathVariable Integer id) {
        return resourceService.get(Resource.getByPath(resource), id);
    }

    @PostMapping(value = "/{resource}", produces = JSON, consumes = JSON)
    public ResponseEntity<Object> createResource(@PathVariable String resource, @RequestBody Object object) {
        Object result = resourceService.create(Resource.getByPath(resource), object);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{resource}/{id}", produces = JSON, consumes = JSON)
    public Object replaceResource(@PathVariable String resource, @PathVariable Integer id, @RequestBody Object object) {
        return resourceService.replace(Resource.getByPath(resource), object, id);
    }

    @PatchMapping(value = "/{resource}/{id}", produces = JSON, consumes = JSON)
    public Object patchResource(@PathVariable String resource, @PathVariable Integer id, @RequestBody Object object) {
        return resourceService.patch(Resource.getByPath(resource), object, id);
    }

    @DeleteMapping(value = "/{resource}/{id}", produces = JSON)
    public Object deleteResource(@PathVariable String resource, @PathVariable Integer id) {
        return resourceService.delete(Resource.getByPath(resource), id);
    }

    @GetMapping(value = "/{resource}/{resourceId}/{nestedResource}", produces = JSON)
    public Object getNestedResourceList(
            @PathVariable String resource,
            @PathVariable Integer resourceId,
            @PathVariable String nestedResource) {
        return resourceService.getAllNested(
                Resource.getByPath(resource),
                resourceId,
                Resource.getByPath(nestedResource, true)
        );
    }

    @PostMapping(value = "/{resource}/{resourceId}/{nestedResource}", produces = JSON, consumes = JSON)
    public Object createNestedResource(
            @PathVariable String resource,
            @PathVariable Integer resourceId,
            @PathVariable String nestedResource,
            @RequestBody Object object) {
        Object result = resourceService.createNested(
                Resource.getByPath(resource),
                resourceId,
                Resource.getByPath(nestedResource),
                object);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
