# JSON Placeholder Wrapper API

### Description
The application is a Spring Boot RESTful service wrapped around the JSON Placeholder API.\
Additionally, a scheduled task stores the data as XML and JSON files in the file system.

### Running the application
Run the following command to start the application:
```shell script
./mvnw spring-boot:run
```
This will start the application on port `8080`.
If you wish to disable the scheduler, set its `enabled` property to false in application.properties.

### Unit Tests
To run all tests:
````shell script
mvn test
````

### Usage
#### REST API
The REST service supports all the resources and HTTP methods offered by JSON Placeholder
and uses the same routes to access the data including the nested routes.
However this API only supports path variables.

The following basic routes support GET, POST, PUT, PATCH and DELETE:
* /users
* /todos
* /posts
* /comments
* /photos
* /albums

The PUT, PATCH and DELETE methods require the resource item's id as a path parameter.
The GET method will return all items if an item id is not passed.

The following nested routes support GET and POST only:
* /posts/1/comments
* /albums/1/photos
* /users/1/albums
* /users/1/todos
* /users/1/posts

#### Data Storage
The data storage service runs as a separate scheduled task.\
You can disable the scheduler by setting its `enabled` property to false in application.properties.

### Implementation Details

The application components are broken down into different layers that allow modularity and
separation of concerns.

#### The REST API

##### The Resource Controller
I chose to use a single controller for all resources and http methods since all requests 
follow an identical pattern, only the resource changes.
The controller does not perform any business logic and merely routes the traffic according to 
resource/method pairs. Action is delegated to the service layer.
The REST API controller uses path variables to dynamically map a request to a resource.
The Resource enum is key to the application's design and holds all the necessary information for
handling a resource's request including the routes and Java class it is mapped to.
The ResourceController uses the Resource.getByPath method to resolve the resource and pass it on 
to the resource service.\
In addition, the ResourceErrorController will handle errors that occur before the service is called.
These will typically be 4xx bad request errors when a request cannot be mapped to a method or when 
a required request body is not present.

##### The Resource Service
The resource service is responsible for making the call to JSON Placeholder and returning the result.
The ResourceService uses the information provided by the Resource enum to make the REST call using a Spring  
RestTemplate. The Resource enum provides the URL and the class the returned object will be mapped to.
The injected RestTemplate is configured with a custom ResponseErrorHandler that will intercept any error 
returned by the rest template and, in turn, throw an application-specific exception according to the 
http response. This allows us to return the appropriate ResponseEntity based on a short list of 
exceptions.

#### Data Storage

##### Service
The DataStorageService is at the center of the data storage system. 
It implements two methods, storeData and retrieveData. Again, the Resource enum is used here as a
parameter to both methods, representing the resource to be stored.\
The service uses a FileLocator singleton to hold information about each file:
createdAt, timeToLive and the location of the file in the file system.
When the service handles a request, it will lookup the FileLocator to see if the file exists for the given
resource and check whether the file needs to be refreshed based on its timeToLive.

##### Repository
The actual file creation is delegated to a FileRepository.
It is limited to writing an object to a file in the given format and retrieving a file given its absolute
path.

##### Scheduler
The DataStorageScheduler runs on a regular basis to store the data.
It uses the DataStorageService to store the data.
It iterates through the relevant resources and saves the data in both XML and JSON formats.

### Improvements
First of all, the data storage service should run in its own standalone application.

Secondly, if we really wanted to keep a file-based data store, we would consider pushing the files 
to an Amazon S3 bucket (or equivalent). In this scenario, we would need to write a new implementation of the
FileRepository.

A different approach would be to push the data to a Redis in-memory data store (or equivalent).
This approach is similar to the file-based approach: in both cases we are using a key to retrieve the data 
(with the file-based approach the key is the file name).
The advantage of the Redis store is that the data (the content of the file) can be directly saved as is.
Another advantage is that we no longer need to handle files. Another argument in favour of the in-memory store is 
the performance factor: retrieving content from Redis is much faster than downloading a file from S3.
In fact, I would only consider using S3 for images, html files or documents that can be 
served as-is, downloaded directly from the S3 server.
Therefore, since the data we are dealing with needs to be parsed, I would recommend using an in-memory data store.
Another huge advantage of storing the data in an in-memory store is that it allows us to use it as a cache.

The question of whether to use a relational database naturally comes to mind.
If we were to offer more functionality such as filtering, sorting and pagination, then we might have to create and 
store our data in a different way.
If we were to return, for example, all the posts containing a particular word in the title, we would have two options.\
The first option would be to query our PostList.json file using JSONPath (or XPath in the case of XML files) and 
return the subset.\
The second option would be to structure the data returned by JSON Placeholder and store it in a relational database.
Once the data is organized into relational tables, the flexibility of SQL allows us infinite possibilities to filter and sort.\
Either way, to boost performance, the result of the query would have to be saved in the in-memory data store.
Although querying the JSON (or XML) data directly using JSONPath (or XPath) is a possibility, this approach is limited
and more complex than running SQL queries. Also, if the query results need to be cached anyway, then we might as well 
leverage the flexibility of a relational database.

As a conclusion, I would say that if we were to improve data storage system, the infrastructure would depend on the 
application's functionality. If we were to offer filtering, sorting and pagination we would probably be better off using 
a database in conjunction with an in-memory store serving as a cache. That way we address the performance issues caused by 
extracting result sets from a query. If, on the other hand, the application were to remain a simple wrapper of the JSON 
Placeholder API then simply using the in-memory database for storage and/or cache would already improve the system drastically.
