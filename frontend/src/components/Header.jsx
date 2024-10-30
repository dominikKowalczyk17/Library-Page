import { Link } from 'react-router-dom';
import Navigation from './Navigation';
import { useEffect, useState } from 'react';

const Header = () => {
  const [isScrolled, setIsScrolled] = useState(false);

  const handleScroll = () => {
    setIsScrolled(window.scrollY > 50);
  };

  useEffect(() => {
    window.addEventListener('scroll', handleScroll);
    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <header
      className={`bg-white sticky top-0 z-10 shadow-lg transition-all duration-300 ${
        isScrolled ? 'h-16 p-4' : 'h-24 p-8'
      }`}
    >
      <div className='flex justify-between container mx-auto h-full items-center'>
        <h1
          className={`font-bold transition-all duration-300 ${
            isScrolled ? 'text-xl' : 'text-2xl'
          }`}
        >
          <Link to='/'>Book Store</Link>
        </h1>
        <Navigation />
      </div>
    </header>
  );
};

export default Header;
