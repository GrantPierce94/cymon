SpringBoot version 2.4.0
JAVA JDK version 11

# ManyToMany example

To see example of ManyToMany, checkout Post class, then Tag class, then all the classes in the course folder.


## @JsonIgnore example
using Postman, try making a post object with params 'title', 'description', 'content', and 'tags'. Then do a get request to get all the newly created post in the database. You should see the returned post object is recursively calling its attributes (tags) and the tags are recursively calling the post => Really big returned json object.
#
To fix this, go to Post class and uncomment '@JsonIgnore' on top of 'tags' attribute. Try the example again and the recursive calls should stop.
