import { Link } from "react-router-dom";

const Breadcrumbs = ({ links }) => {
  return (
    <nav className="p-2 rounded-md py-5 flex justify-center items-center text-sm opacity-50">
      {links.map((link, index) => (
        <span key={index}>
          {link.path ? (
            <Link to={link.path} className="hover:opacity-45 text-black">
              {link.name}
            </Link>
          ) : (
            <span className="text-black font-semibold">{link.name}</span>
          )}
          {index < links.length - 1 && <span className="mx-2"> / </span>}
        </span>
      ))}
    </nav>
  );
};

export default Breadcrumbs;
