import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const Categories = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchCategories = async () => {
    setLoading(true);
    try {
      const response = await fetch('http://localhost:8081/api/categories');

      // Check if response is ok
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      setCategories(data);
      setError(null); // Clear previous error if fetching is successful
    } catch (error) {
      setError('Something went wrong. Please refresh the categories.');
      console.error('Error fetching categories:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCategories(); // Fetch categories on mount
  }, []);

  return (
    <div className='p-6 bg-transparent'>
      <header className='mb-6 text-center'>
        <h2 className='text-3xl font-serif font-bold text-[#6b4f2c]'>
          Categories
        </h2>
        <p className='mt-2 text-lg text-gray-700'>Explore books by category</p>
      </header>
      {loading && (
        <p className='text-lg text-gray-600'>Loading categories...</p>
      )}
      {error && (
        <div className='text-red-500 flex flex-col items-center justify-center px-3 text-center'>
          <p>{error}</p>
          <button
            onClick={fetchCategories}
            className='mt-4 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition-colors'
          >
            Refresh
          </button>
        </div>
      )}
      <ul className='grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4'>
        {!loading &&
          !error &&
          categories.map((category) => (
            <li
              key={category.id}
              className='border rounded-lg p-4 bg-white shadow-md hover:shadow-lg transition-shadow duration-300'
            >
              <Link
                to={`/categories/${category.id}`}
                className='block text-center text-lg text-[#6b4f2c] font-semibold'
              >
                {category.name}
              </Link>
            </li>
          ))}
      </ul>
    </div>
  );
};

export default Categories;
