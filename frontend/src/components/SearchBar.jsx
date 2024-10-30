const SearchBar = () => {
  return (
    <div className='relative'>
      <input
        type='text'
        placeholder='Search for books...'
        className='p-2 border border-gray-300 rounded-md w-full md:w-1/2'
      />
      <button className='absolute right-0 top-0 mt-1 mr-1 bg-blue-500 text-white px-3 py-2 rounded-md'>
        Search
      </button>
    </div>
  );
};

export default SearchBar;
