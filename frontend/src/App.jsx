import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import MainPage from './pages/MainPage';
import BookPage from './pages/BookPage';
import CatalogPage from './pages/CatalogPage';

function App() {
  return (
    <Router>
      <div className='min-w-full min-h-full'>
        <Routes>
          <Route path='/' element={<MainPage />} />
          <Route path='/books/:isbn' element={<BookPage />} />
          <Route path='/catalog' element={<CatalogPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
