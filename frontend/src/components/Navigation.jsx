import { Link } from 'react-router-dom';

const Navigation = ({ className }) => {
  return (
    <nav
      className={`top-8 flex gap-8 text-lg text-antiqueGold font-bold ${className}`}
    >
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
  );
};

export default Navigation;
