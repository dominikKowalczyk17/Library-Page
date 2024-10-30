import { Link } from 'react-router-dom';
import 'react-responsive-carousel/lib/styles/carousel.min.css'; // requires a loader
import { Carousel } from 'react-responsive-carousel';

const BookList = ({ books }) => {
  return (
    <Carousel
      axis='horizontal'
      showArrows={true}
      showStatus={false}
      showThumbs={false}
      infiniteLoop={true}
      autoPlay={true}
      interval={50000}
      centerMode={false}
    >
      {books.map((book) => (
        <Link
          to={`/books/${book.isbn}`}
          className='mt-2 block bg-indigo-200'
          key={book.isbn}
        >
          <div className='border rounded-lg p-6 shadow-md hover:shadow-lg transition-shadow duration-300 cursor-pointer'>
            <h3 className='text-lg font-semibold'>{book.name}</h3>
            <p className='text-gray-600'>Author: {book.author}</p>
            <p className='text-gray-500'>Genre: {book.genre}</p>
            <p className='text-sm'>{book.description}</p>
            View Details
          </div>
        </Link>
      ))}
    </Carousel>
  );
};

export default BookList;
