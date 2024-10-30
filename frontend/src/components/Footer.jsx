import { Link } from 'react-router-dom';

const Footer = () => {
  return (
    <footer className='bg-gray-800 text-white py-6 mt-12'>
      <div className='container mx-auto text-center'>
        <p className='mb-4'>
          &copy; {new Date().getFullYear()} My Book Store. All rights reserved.
        </p>
        <nav>
          <Link to='/' className='text-white hover:underline mx-2'>
            Home
          </Link>
          <Link to='/catalog' className='text-white hover:underline mx-2'>
            Catalog
          </Link>
          <Link to='/about' className='text-white hover:underline mx-2'>
            About Us
          </Link>
          <Link to='/contact' className='text-white hover:underline mx-2'>
            Contact
          </Link>
        </nav>
      </div>
    </footer>
  );
};

export default Footer;
