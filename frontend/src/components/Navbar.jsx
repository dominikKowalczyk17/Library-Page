import { Link } from 'react-router-dom';
import { useState } from 'react';
import { AiOutlineClose, AiOutlineMenu } from 'react-icons/ai';

const Navbar = () => {
  const [nav, setNav] = useState(false);

  const handleNav = () => {
    setNav(!nav);
  };

  const navItems = [
    { id: 1, text: 'Strona główna', path: '/' },
    { id: 2, text: 'Ulubione', path: '/favorites' },
    { id: 3, text: 'Kategorie', path: '/categories' },
    { id: 4, text: 'O nas', path: '/about' },
    { id: 5, text: 'Kontakt', path: '/contact' },
  ];

  return (
    <div className='bg-neutral-400 flex justify-between items-center h-24 mx-auto px-4 text-white'>
      <h1 className='w-full text-3xl font-bold'>Library App</h1>

      <ul className='hidden md:flex w-3/5'>
        {navItems.map((item) => (
          <li
            key={item.id}
            className='p-5 rounded-xl m-2 cursor-pointer duration-300 hover:text-black'
          >
            <Link to={item.path}>{item.text}</Link>
          </li>
        ))}
      </ul>

      <div onClick={handleNav} className='block md:hidden'>
        {nav ? <AiOutlineClose size={20} /> : <AiOutlineMenu size={20} />}
      </div>

      <ul
        className={
          nav
            ? 'fixed md:hidden left-0 top-0 w-[60%] h-full border-r border-r-gray-900 bg-[#000300] ease-in-out duration-500'
            : 'ease-in-out w-[60%] duration-500 fixed top-0 bottom-0 left-[-100%]'
        }
      >
        <h1 className='w-full text-3xl font-bold m-4'>Library App</h1>
        {navItems.map((item) => (
          <li
            key={item.id}
            className='p-4 border-b rounded-xl duration-300 hover:text-black cursor-pointer border-gray-600'
          >
            <Link to={item.path} onClick={() => setNav(false)}>
              {item.text}
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Navbar;
