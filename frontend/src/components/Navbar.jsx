import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
  const { user, logout, isAdmin } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav style={styles.nav}>
      <div className="container" style={styles.navContent}>
        <Link to="/" style={styles.brand}>
          🍬 Sweet Shop
        </Link>
        
        {user && (
          <div style={styles.navLinks}>
            <Link to="/" style={styles.link}>
              Shop
            </Link>
            {isAdmin() && (
              <Link to="/admin" style={styles.link}>
                Admin Panel
              </Link>
            )}
            <span style={styles.username}>
              Hi, {user.username}
            </span>
            <button onClick={handleLogout} className="btn btn-danger" style={styles.logoutBtn}>
              Logout
            </button>
          </div>
        )}
      </div>
    </nav>
  );
};

const styles = {
  nav: {
    backgroundColor: '#4CAF50',
    color: 'white',
    padding: '15px 0',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
  },
  navContent: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  brand: {
    fontSize: '24px',
    fontWeight: 'bold',
    color: 'white',
    textDecoration: 'none',
  },
  navLinks: {
    display: 'flex',
    alignItems: 'center',
    gap: '20px',
  },
  link: {
    color: 'white',
    textDecoration: 'none',
    fontSize: '16px',
    fontWeight: '500',
  },
  username: {
    fontSize: '14px',
    fontWeight: '500',
  },
  logoutBtn: {
    fontSize: '14px',
    padding: '8px 16px',
  },
};

export default Navbar;
