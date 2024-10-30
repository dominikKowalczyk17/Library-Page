import { Link } from 'react-router-dom';
import SearchBar from '../components/SearchBar';
import backgroundImage from '../assets/library.png';
import arrow from '../assets/arrow.png';

const MainPage = () => {
  return (
    <div
      className='relative min-h-screen flex flex-col items-center justify-center text-white bg-cover bg-center'
      style={{ backgroundImage: `url(${backgroundImage})` }}
    >
      {/* Dimmed Overlay */}
      <div className='absolute inset-0 bg-black opacity-40'></div>
      {/* Navbar */}
      <nav className='absolute top-8 flex gap-8 text-lg text-antiqueGold font-bold'>
        <Link to='/about' className='hover:opacity-50'>
          About
        </Link>
        <Link to='/events' className='hover:opacity-50'>
          Events
        </Link>
        <Link to='/services' className='hover:opacity-50'>
          Services
        </Link>
        <Link to='/contacts' className='hover:opacity-50'>
          Contacts
        </Link>
      </nav>
      {/* Main Heading */}
      <h1 className='font-custom text-9xl text-center relative w-5/12 bg-clip-text text-transparent bg-gradient-to-r from-[#BFA675] to-[#D6C2A0] shadow-lg'>
        Library of Antique Literature
      </h1>
      {/* Arrow Link to Catalog Page */}
      <div className='absolute bottom-8 right-28 flex flex-col items-center'>
        <Link
          to='/catalog'
          className='flex items-center text-sm text-antiqueGold font-bold gap-6 transition-transform duration-300 ease-in-out hover:translate-x-2'
        >
          <span>View Catalog</span>
          <span
            className='arrow mb-2 mt-2 w-16 h-16 bg-bottom bg-contain text-antiqueGold'
            style={{ backgroundImage: `url(${arrow})` }}
          ></span>
        </Link>
      </div>
    </div>
  );
};

export default MainPage;
