// src/pages/CatalogPage.js

import { useEffect, useState } from 'react';
import BookList from '../components/BookList'; // Adjust path if necessary
import Categories from '../components/Categories'; // Adjust path if necessary
import Header from '../components/Header'; // Import Header component

const CatalogPage = () => {
  const [popularBooks, setPopularBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null); // Error state added

  useEffect(() => {
    const fetchPopularBooks = async () => {
      setLoading(true); // Set loading state
      try {
        const response = await fetch('http://localhost:8081/api/books/popular');

        // Check if response is ok
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data = await response.json();
        setPopularBooks(data);
      } catch (error) {
        setError('Error fetching popular books. Please try again later.'); // Set error state
        console.error('Error fetching popular books:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchPopularBooks();
  }, []);

  return (
    <div className='flex flex-col min-h-screen'>
      <Header /> {/* Add Header here */}
      <div className='flex flex-1 p-6 bg-color'>
        {/* Categories Component */}
        <div className='w-1/4 p-4'>
          <Categories />
        </div>

        {/* Books Section */}
        <div className='w-3/4 p-4'>
          <header className='mb-8 text-center'>
            <h1 className='text-4xl font-serif font-bold text-[#6b4f2c]'>
              Catalog
            </h1>
            <p className='mt-2 text-lg text-gray-700'>
              Discover popular books in our collection.
            </p>
          </header>
          {loading && <p className='text-lg text-gray-600'>Loading...</p>}
          {error && <p className='text-red-500'>{error}</p>}{' '}
          {/* Displaying error */}
          {!loading && !error && <BookList books={popularBooks} />}
        </div>
      </div>
    </div>
  );
};

export default CatalogPage;
