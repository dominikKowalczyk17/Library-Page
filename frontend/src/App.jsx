import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import MainPage from './pages/MainPage';
import BookPage from './pages/BookPage';
import Navbar from './components/Navbar';

function App() {
  return (
    <Router>
      <div className='min-w-full min-h-full'>
        <Navbar />
        <Routes>
          <Route path='/' element={<MainPage />} />
          <Route path='/books/:isbn' element={<BookPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
