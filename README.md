# Configurable back end

Configurable backend manages a set of collections for 
storing data.  As a user, you are able to configure collections 
as a result get a public Web API over HTTP.  

# Vocabulary

### Collection

A collection contains a set of documents.
A collection has a schema that specifies the data structure 
to which each of the documents has to comply.
A collection also has a set of operations it exposes to 
create, read, update and delete documents.

### Document

A document represents a JSON object.  To be stored in 
a collection, a document must be valid according to the 
collection's data type.

### Type system

The type system is the collection of all data types known 
in the server.  There are 7 basic types like int, float, 
string, boolean, id, object, list 

### Operation

A public API request.  Each collection exposes a 
set of operations for managing it's data.

### Webhooks

Each operation is also a webhook to which listeners can be 
subscribed.  Listeners are notified over HTTP which means 
they can be implemented in any language. 

# Web API

The collection of all operations is the Web API exposed 
buy the server. 

# Versioning

As your application evolves you can change the data types 
of your collections and the configurable back end will handle 
the versions.

# Monitoring

It should be easy to plug in the configurable backend into 
monitoring solutions.