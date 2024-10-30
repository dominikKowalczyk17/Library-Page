import { Link } from 'react-router-dom';

const BookList = ({ books }) => {
  return (
    <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4'>
      {books.map((book) => (
        <div
          key={book.isbn}
          className='border rounded-lg p-4 shadow-md hover:shadow-lg transition-shadow duration-300'
        >
          <h3 className='text-lg font-semibold'>{book.name}</h3>
          <p className='text-gray-600'>Author: {book.author}</p>
          <p className='text-gray-500'>Genre: {book.genre}</p>
          <p className='text-sm'>{book.description}</p>
          <Link
            to={`/books/${book.isbn}`}
            className='text-blue-500 hover:underline mt-2 block'
          >
            View Details
          </Link>
        </div>
      ))}
    </div>
  );
};

export default BookList;
