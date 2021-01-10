package apgrison.codingtask.service;

import apgrison.codingtask.model.Resource;

/**
 * The resource service handles all operations supported by JSON Placeholder
 */
public interface ResourceService {

    Object getAll(Resource resource);
    Object get(Resource resource, Integer id);
    Object create(Resource resource, Object object);
    Object replace(Resource resource, Object object, Integer id);
    Object patch(Resource resource, Object object, Integer id);
    Object delete(Resource resource, Integer id);
    Object getAllNested(Resource resource, Integer id, Resource nested);
    Object createNested(Resource resource, Integer id, Resource nested, Object object);
}
