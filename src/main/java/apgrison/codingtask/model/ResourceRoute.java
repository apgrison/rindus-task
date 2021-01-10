package apgrison.codingtask.model;

public enum ResourceRoute {

    USERS("users"),
    POSTS("posts"),
    COMMENTS("comments"),
    ALBUMS("albums"),
    PHOTOS("photos"),
    TODOS("todos"),
    ;

    private static class Constants {
        public static final String URL = "https://jsonplaceholder.typicode.com";
    }

    private final String path;

    ResourceRoute(String path) {
        this.path = path;
    }

    public static ResourceRoute getByPath(String path) {
        if (path == null) {
            return null;
        }
        for (ResourceRoute route : ResourceRoute.values()) {
            if (route.path().equals(path)) {
                return route;
            }
        }
        return null;
    }

    public String path() {
        return path;
    }

    public String endpoint() {
        return Constants.URL + "/" + path;
    }
}
