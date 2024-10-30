import { useEffect, useState } from 'react';
import BookList from './BookList';

const CatalogPage = () => {
  const [popularBooks, setPopularBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPopularBooks = async () => {
      try {
        const response = await fetch('http://localhost:8081/api/books/popular');
        const data = await response.json();
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
      <header className='mb-4 text-center'>
        <h1 className='text-3xl font-bold'>Catalog</h1>
        <p className='mt-2 text-gray-700'>
          Discover popular books in our collection.
        </p>
      </header>

      {loading ? <p>Loading...</p> : <BookList books={popularBooks} />}
    </div>
  );
};

export default CatalogPage;
