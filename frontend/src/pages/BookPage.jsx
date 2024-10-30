import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Header from '../components/Header';
import Breadcrumbs from '../components/Breadcrumbs';

const BookPage = () => {
  const { isbn } = useParams();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBookDetails = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await fetch(`http://localhost:8081/api/books/${isbn}`);

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        setBook(data);
      } catch (error) {
        console.error('Error fetching book details:', error);
        setError('Something went wrong. Please try again later.');
      } finally {
        setLoading(false);
      }
    };

    fetchBookDetails();
  }, [isbn]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p className='text-red-500'>{error}</p>;
  if (!book) return <p>Book not found.</p>;

  return (
    <section>
      <Header />
      <Breadcrumbs
        links={[
          { name: 'Home', path: '/' },
          { name: 'Catalog', path: '/catalog' },
          { name: book.name, path: `/catalog/${isbn}` }, // Assuming you have a dynamic path for the book details
        ]}
      />
      <main className='bg-color p-6 md:p-12'>
        <div className='flex flex-col md:flex-row items-start'>
          {/* Book Cover */}
          <div className='md:w-1/3 mb-6 md:mb-0'>
            <img
              src={book.coverImageUrl}
              alt={book.name}
              className='w-full h-auto rounded-lg shadow-lg'
            />
          </div>

          {/* Book Info */}
          <div className='md:w-2/3 md:pl-8'>
            <h1 className='text-4xl font-bold mb-2'>{book.name}</h1>
            <div className='text-gray-600 mb-1'>
              Author: <span className='font-semibold'>{book.author}</span>
            </div>
            <div className='text-gray-600 mb-1'>
              Genre: <span className='font-semibold'>{book.genre}</span>
            </div>
            <div className='text-gray-600 mb-4'>
              Popularity Score:{' '}
              <span className='font-semibold'>{book.popularityScore}</span>
            </div>
            <p className='text-lg mb-4'>{book.description}</p>
            <button className='bg-[#6b4f2c] text-white py-2 px-4 rounded hover:bg-[#5a3e2b] transition duration-300'>
              Add to Cart
            </button>
          </div>
        </div>
      </main>
    </section>
  );
};

export default BookPage;
