import { useEffect, useState } from 'react';
import BookList from './BookList';
import SearchBar from '../components/SearchBar';

const MainPage = () => {
  const [popularBooks, setPopularBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPopularBooks = async () => {
      try {
        const response = await fetch('http://localhost:8081/api/books/popular');
        const data = await response.json();
        // Assuming the response contains an array of books
        setPopularBooks(data);
      } catch (error) {
        console.error('Error fetching popular books:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchPopularBooks();
  }, []);

  return (
    <div className='p-4'>
      <header className='mb-4'>
        <h1 className='text-3xl font-bold'>Welcome to the Library</h1>
        <p className='mt-2 text-gray-700'>
          Explore our collection of books and find your next read!
        </p>
        <SearchBar />
      </header>

      <section className='mt-6'>
        <h2 className='text-2xl font-semibold mb-2'>Popular Books</h2>
        {loading ? <p>Loading...</p> : <BookList books={popularBooks} />}
      </section>
    </div>
  );
};

export default MainPage;
