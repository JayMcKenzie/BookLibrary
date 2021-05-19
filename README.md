# Book Library Application

The goal of the project is to make a book library management platform, that allows to share information about books occuring in the library. With this application, it is possible to:
* get the book by its ISBN-13 value (or its ID, if ISBN is not included in database),
* get the list of books that are included in specified category,
* get the first book that has higher numer of pages than set in parameter,
* get the list of books that can be read within a month – user specifies the average number of pages that can be read within a hour, and average time (in hours) that can be spent on reading during the day. Books appear in the descending order, depending on its average rating,
* ~~get the list of all authors in descending order, depending on average rating of their books.~~ 
* get the set of 5 recently searched books by ISBN. The list clears itself after every hour

# Framework

The framework used to create the app is **Spring** – the open-source framework for the Java platform, that allows making web applications.

# Testing

To test the correctness of implemented methods, **JUnit 4** was used – it is the tool created to make unit tests for the applications created in Java.


# Code quality

To get the best code quality, **SonarLint** was used - it is the extension that suggest modifications in code, so it could get look and work much better.

# Building

To build the app use following command:
```mvn clean package```

# Running

After building the app run following command to start it:
```java -jar target/wtt-app.jar```

# Access

To load the JSON file into the project, send the GET request to the address:
```http://localhost:8080/book/init```

To get the book by its isbn, send the GET request to the address:
```http://localhost:8080/book/isbn/{isbn}```

To get the list of books by its category, send the GET request to the address:
```http://localhost:8080/book/category/{category}```

To get the first book appearing in JSON file, that has more pages that client asks in the argument, send the GET request to the address:
```http://localhost:8080/book/pagecount/{number}```

To get the list of books possible to read in 30 days, send the GET request to the address:
```http://localhost:8080/book/{pagesPerHour}/{averageNumberOfHoursPerDay}```

To get the set of previously searched books, send the GET request to the address:
```http://localhost:8080/book/recent```

