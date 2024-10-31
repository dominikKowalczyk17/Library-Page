import {
  BrowserRouter as Router,
  Route,
  Routes,
  useLocation,
} from "react-router-dom";
import { AnimatePresence, motion } from "framer-motion";
import MainPage from "./pages/MainPage";
import BookPage from "./pages/BookPage";
import CatalogPage from "./pages/CatalogPage";

function App() {
  const location = useLocation(); // Get the current location

  // Define motion variants for sliding animations
  const variants = {
    initial: (direction) => ({
      x: direction > 0 ? "100%" : "-100%", // Slide from right or left
      opacity: 0,
    }),
    animate: {
      x: 0,
      opacity: 1,
    },
    exit: (direction) => ({
      x: direction < 0 ? "100%" : "-100%", // Slide out to right or left
      opacity: 0,
    }),
  };

  return (
    <div className="min-w-full min-h-full">
      <AnimatePresence>
        <Routes location={location} key={location.key}>
          <Route
            path="/"
            element={
              <motion.div
                custom={1} // Direction for the initial animation (1 means to the right)
                variants={variants}
                initial="initial"
                animate="animate"
                exit="exit"
                transition={{ duration: 0.5 }} // Animation duration
              >
                <MainPage />
              </motion.div>
            }
          />
          <Route
            path="/books/:isbn"
            element={
              <motion.div
                custom={-1} // Direction for the initial animation (negative means to the left)
                variants={variants}
                initial="initial"
                animate="animate"
                exit="exit"
                transition={{ duration: 0.5 }} // Animation duration
              >
                <BookPage />
              </motion.div>
            }
          />
          <Route
            path="/catalog"
            element={
              <motion.div
                custom={-1} // Direction for the initial animation (negative means to the left)
                variants={variants}
                initial="initial"
                animate="animate"
                exit="exit"
                transition={{ duration: 0.5 }} // Animation duration
              >
                <CatalogPage />
              </motion.div>
            }
          />
        </Routes>
      </AnimatePresence>
    </div>
  );
}

export default App;
