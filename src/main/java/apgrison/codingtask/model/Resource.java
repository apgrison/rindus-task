package apgrison.codingtask.model;

import java.util.stream.Stream;

public enum Resource {

    USERS(User.class, false, ResourceRoute.USERS),
    POSTS(Post.class, false, ResourceRoute.POSTS),
    ALBUMS(Album.class, false, ResourceRoute.ALBUMS),
    PHOTOS(Photo.class, false, ResourceRoute.PHOTOS),
    COMMENTS(Comment.class, false, ResourceRoute.COMMENTS),
    TODOS(Todo.class, false, ResourceRoute.TODOS),
    USERS_LIST(User[].class, true, ResourceRoute.USERS),
    POSTS_LIST(Post[].class, true, ResourceRoute.POSTS),
    ALBUMS_LIST(Album[].class, true, ResourceRoute.ALBUMS),
    PHOTOS_LIST(Photo[].class, true, ResourceRoute.PHOTOS),
    COMMENTS_LIST(Comment[].class, true, ResourceRoute.COMMENTS),
    TODOS_LIST(Todo[].class, true, ResourceRoute.TODOS),
    ;

    private final Class<?> type;
    private final boolean isList;
    private final ResourceRoute route;

    Resource(Class<?> type, boolean isList, ResourceRoute route) {
        this.type = type;
        this.isList = isList;
        this.route = route;
    }

    private static Resource getByRoute(ResourceRoute route, boolean list) {
        if (route == null) {
            return null;
        }
        String name = route.name();
        if (list) {
            name += "_LIST";
        }
        for (Resource resource : values()) {
            if (resource.name().equals(name.toUpperCase())) {
                return resource;
            }
        }
        return null;
    }

    public static Resource getByPath(String path) {
        return getByRoute(ResourceRoute.getByPath(path), false);
    }

    public static Resource getByPath(String path, boolean list) {
        return getByRoute(ResourceRoute.getByPath(path), list);
    }

    public static Resource[] getListResources() {
        return Stream.of(Resource.values()).filter(Resource::isList).toArray(Resource[]::new);
    }

    public Class<?> getType() {
        return type;
    }

    public String getPath() {
        return route.path();
    }

    public String getEndpoint() {
        return route.endpoint();
    }

    public boolean isList() {
        return isList;
    }
}
