import { Link } from 'react-router-dom';

const BookList = ({ books }) => {
  const getImageUrl = (isbn) => {
    const serverUrl = 'http://localhost:8081/api/books/image';
    return `${serverUrl}/${isbn}`;
  };

  return (
    <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8'>
      {books.map((book) => (
        <Link
          to={{
            pathname: `/books/${book.isbn}`,
            state: { coverImageUrl: getImageUrl(book.isbn) }, // Pass the image URL here
          }}
          className='block transition-transform transform hover:scale-105'
          key={book.isbn}
        >
          <div className='border rounded-lg overflow-hidden shadow-lg hover:shadow-xl transition-shadow duration-300 cursor-pointer bg-white'>
            <div
              className='h-64 bg-cover bg-center'
              style={{
                backgroundImage: `url(${getImageUrl(book.isbn)})`,
              }}
            >
              {/* Placeholder for book cover image */}
            </div>
            <div className='p-4'>
              <h3 className='text-xl font-semibold text-[#6b4f2c]'>
                {book.title}
              </h3>
              <p className='text-gray-700'>Author: {book.author}</p>
              <p className='text-gray-600'>Genre: {book.genre}</p>
              <p className='text-sm text-gray-500 mt-2'>{book.description}</p>
              <p className='mt-4 text-[#6b4f2c] font-bold'>View Details</p>
            </div>
          </div>
        </Link>
      ))}
    </div>
  );
};

export default BookList;
