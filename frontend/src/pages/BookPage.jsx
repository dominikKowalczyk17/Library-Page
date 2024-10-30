import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const BookPage = () => {
  const { isbn } = useParams();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null); // State for error handling

  useEffect(() => {
    const fetchBookDetails = async () => {
      setLoading(true);
      setError(null); // Reset error state before fetching
      try {
        const response = await fetch(`http://localhost:8081/api/books/${isbn}`); // Adjust your endpoint if needed

        // Check if the response is ok (status 200-299)
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        setBook(data);
      } catch (error) {
        console.error('Error fetching book details:', error);
        setError(error.message); // Set error message
      } finally {
        setLoading(false);
      }
    };

    fetchBookDetails();
  }, [isbn]);

  if (loading) return <p>Loading...</p>;

  if (error) return <p>Error: {error}</p>; // Display error message

  if (!book) return <p>Book not found.</p>; // Fallback if no book is found

  return (
    <main className='bg-slate-400 p-4'>
      <h1 className='text-3xl font-bold'>{book.name}</h1>
      <div className='text-gray-600'>Author: {book.author}</div>
      <div className='text-gray-500'>Genre: {book.genre}</div>
      <div className='mt-2'>{book.description}</div>
      <div className='mt-4'>Popularity Score: {book.popularityScore}</div>
    </main>
  );
};

export default BookPage;
