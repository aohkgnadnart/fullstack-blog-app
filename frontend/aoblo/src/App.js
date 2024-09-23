// src/App.js
import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './pages/HomePage';
import LikeListPage from './pages/LikeListPage'; // Import the new component
import Login from './pages/Login';
import Register from './pages/Register';
import CreatePost from './pages/CreatePost';
import EditPost from './pages/EditPost';
import PostDetail from './pages/PostDetail';
import MessagePage from './pages/MessagePage'; // Import MessagePage
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import PrivateRoute from './components/PrivateRoute';
import Footer from './components/Footer';
import TopHeader from './components/Header/TopHeader';
import NavHeader from './components/Header/NavHeader';
import UserProfile from './pages/UserProfile';
import ProfilePage from './pages/ProfilePage';
import ChangePasswordPage from './pages/ChangePasswordPage';
import FollowingFeed from './pages/FollowingFeed';
import AllUsers from './pages/AllUsers';
import { fetchMe } from './apis/userApi';

export const UserContext = React.createContext();

const App = () => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      fetchMe(token)
        .then(response => {
          setUser(response.data);
        })
        .catch(() => {
          localStorage.removeItem('accessToken');
        })
        .finally(() => {
          setLoading(false);
        });
    } else {
      setLoading(false);
    }
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <UserContext.Provider value={{ user, setUser }}>
      <Router>
        <ToastContainer
          position="top-center"
          autoClose={3000}
          hideProgressBar={false}
          closeOnClick={true}
          pauseOnHover={true}
          draggable={true}
          progress={undefined}
        />
        <div id="root">
          <TopHeader />
          <div className="main-content" style={{ minHeight: '100vh' }}>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/create-post" element={<PrivateRoute element={<CreatePost />} />} />
              <Route path="/posts/:id" element={<PostDetail />} />
              <Route path="/users/:id" element={<UserProfile />} />
              <Route path="/profile" element={<PrivateRoute element={<ProfilePage />} />} />
              <Route path="/change-password" element={<PrivateRoute element={<ChangePasswordPage />} />} />
              <Route path="/following-feed" element={<PrivateRoute element={<FollowingFeed />} />} />
              <Route path="/all-users" element={<PrivateRoute element={<AllUsers />} requiredRole="ROLE_ADMIN" />} />
              <Route path="/message/:userId" element={<MessagePage />} /> {/* Thêm tuyến đường mới */}
              <Route path="/posts/:id/edit" element={<PrivateRoute element={<EditPost />} />} />
              <Route path="/posts/:postId/likes" element={<LikeListPage />} /> {/* New route */}
            </Routes>
          </div>
          <Footer />
        </div>
      </Router>
    </UserContext.Provider>
  );
};

export default App;
