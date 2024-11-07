Library App
  This project is a React-based Library App built with React Router, 
  TailwindCSS, and a simulated REST API for fetching book and category data. Users can explore book categories, search for books, 
  and navigate through different sections of the app.

Features
  Navigation Bar: Responsive Navbar with links to Home, Favorites, Categories, About, and Contact.
  Search Functionality: Search bar for filtering books.
  Book Categories: Displays book categories fetched from a simulated API.
  Book List: List of books displayed in grid format, with details and cover images.
  Breadcrumbs: Navigation breadcrumbs for easier navigation within nested views.
  Footer: Simple footer with links to main sections and copyright info.
  
Installation
 1. Clone the repository:
      git clone https://github.com/your-username/library-app.git
      cd library-app

  2. Install dependencies:
      npm install

  3. Run the app:
      npm run dev
The app should now be running at http://localhost:5173.

Set up backend (mandatory for content to display):

If using a backend, update the API URLs in Categories.jsx and BookList.jsx to point to your backend server.

Components Overview
  Breadcrumbs: Dynamic navigation component to show the path to the current page.
  BookList: Displays a list of books with cover image, author, genre, and a short description.
  Categories: Fetches and lists available book categories with error handling and loading states.
  Footer: A footer containing links to other parts of the app.
  Header: Sticky header with an animation that changes size on scroll.
  Navbar: Responsive navigation bar with a mobile-friendly dropdown.
  SearchBar: Search bar component to help users filter books by title or keyword.

Dependencies
  React: Core library for building the UI
  React Router: For client-side routing
  TailwindCSS: For styling
  React Icons: For including icons in the Navbar

Contribution
Feel free to open an issue or submit a pull request for any features, improvements, or bug fixes.

----------------------------------------------------------------------------------------------------------------------------------------------------------
Library API - Java Backend

This Java-based API provides backend functionality for managing a library of books. Designed to simulate core library operations, it allows for adding, 
searching, and retrieving book details, accessing popular books, and serving book cover images. 
The API uses Java's built-in HTTP server and connects to a PostgreSQL database for storing book data.

Features

1. Add a Book (POST /api/books/add)
    Adds a new book to the library with details such as title, genre, description, author, and image path.
    Generates a random ISBN if one is not provided.
    Stores the new book data in a PostgreSQL database using a simple repository pattern.
2. Get Book Details (GET /api/books/{isbn})
    Retrieves detailed information about a specific book by its ISBN.
    Returns book data in JSON format, including title, genre, description, author, and popularity score.
3. Search Books (GET /api/books/search?keyword={keyword})
    Allows searching for books by title.
    Returns a list of matching books in JSON format.
4. Popular Books (GET /api/books/popular)
    Retrieves a list of the most popular books, determined by their popularity score.
    Limited to the top 10 most popular books in descending order of popularity.
5. Get Book Image (GET /api/books/image/{isbn})
    Serves the cover image for a specific book by its ISBN.
    Supports .jpg, .jpeg, and .jfif image formats.

Database Integration

The API connects to a PostgreSQL database to manage book data. The BookRepository class handles database operations such as adding books, 
searching for books, and retrieving popular books.

Database Configuration
    The database connection settings (URL, username, and password) are configured in the config.properties file.
    Ensure you have a PostgreSQL instance running, and the database is set up with a table called books that stores information like ISBN, title, genre, description, author, and popularity score.

Project Structure
    Handlers: Each handler (e.g., AddBookHandler, GetBookHandler, SearchBooksHandler) defines a separate endpoint and encapsulates the request handling logic.
    Models: Book model represents book data, with methods to convert instances to JSON.
    Repository: BookRepository handles database operations, including adding books, searching by keywords, and retrieving popular books.

Setup and Dependencies
    Database: PostgreSQL is used for storing book information. Configure your database connection in config.properties.
    JSON Libraries: Uses org.json and Gson for JSON parsing and serialization.
    HTTP Server: Built using Java's HttpServer class for lightweight deployment.

Running the Server
  1. Ensure Java (JDK 11+) and PostgreSQL are installed.
  2. Configure your PostgreSQL database settings in config.properties (e.g., db.url, db.username, db.password).
  3. Make sure the PostgreSQL database is running and the books table is set up.
  4. Run the server using the SimpleHttpServer class:
      javac -d . *.java
      java com.libapp.server.SimpleHttpServer
The server will start on http://localhost:8081.

Example Requests:
  Add a Book:
      POST http://localhost:8081/api/books/add
      Content-Type: application/json
      Body: {
          "title": "Sample Book",
          "genre": "Fiction",
          "description": "A thrilling tale.",
          "author": "Jane Doe",
          "image_path": "path/to/image.jpg"
      }
  Get Book Details:
    GET http://localhost:8081/api/books/12345
  Search Books:
    GET http://localhost:8081/api/books/search?keyword=thrilling
  Popular Books:
    GET http://localhost:8081/api/books/popular
  Get Book Image:
    GET http://localhost:8081/api/books/image/12345

License
This project is open-source and available for modification and distribution.
