import React, { useState, useEffect } from 'react';
import { sweetAPI } from '../services/api';

const SweetList = () => {
  const [sweets, setSweets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('');

  const categories = ['CHOCOLATE', 'CANDY', 'GUMMY', 'COOKIE', 'CAKE', 'OTHER'];

  useEffect(() => {
    fetchSweets();
  }, []);

  const fetchSweets = async () => {
    try {
      setLoading(true);
      const response = await sweetAPI.getAll();
      setSweets(response.data);
      setError('');
    } catch (err) {
      setError('Failed to load sweets');
    } finally {
      setLoading(false);
    }
  };

  const handlePurchase = async (id, name) => {
    const quantity = prompt(`How many ${name} would you like to purchase?`, '1');
    if (!quantity || isNaN(quantity) || quantity < 1) return;

    try {
      await sweetAPI.purchase(id, parseInt(quantity));
      setSuccess(`Successfully purchased ${quantity} ${name}!`);
      fetchSweets();
      setTimeout(() => setSuccess(''), 3000);
    } catch (err) {
      setError(err.response?.data?.message || 'Purchase failed');
      setTimeout(() => setError(''), 3000);
    }
  };

  const filteredSweets = sweets.filter(sweet => {
    const matchesSearch = sweet.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         sweet.description?.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = !selectedCategory || sweet.category === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  if (loading) {
    return <div className="spinner"></div>;
  }

  return (
    <div className="container">
      <h1 style={styles.title}>Sweet Shop 🍬</h1>

      {error && <div className="alert alert-error">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      <div className="card" style={styles.filterCard}>
        <div style={styles.filters}>
          <div style={styles.searchBox}>
            <input
              type="text"
              placeholder="Search sweets..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              style={styles.searchInput}
            />
          </div>
          <select
            value={selectedCategory}
            onChange={(e) => setSelectedCategory(e.target.value)}
            style={styles.categorySelect}
          >
            <option value="">All Categories</option>
            {categories.map(cat => (
              <option key={cat} value={cat}>{cat}</option>
            ))}
          </select>
        </div>
      </div>

      <div style={styles.grid}>
        {filteredSweets.length === 0 ? (
          <p style={styles.noResults}>No sweets found</p>
        ) : (
          filteredSweets.map(sweet => (
            <div key={sweet.id} className="card" style={styles.sweetCard}>
              <h3 style={styles.sweetName}>{sweet.name}</h3>
              <p style={styles.category}>{sweet.category}</p>
              <p style={styles.description}>{sweet.description}</p>
              <div style={styles.details}>
                <span style={styles.price}>₹{sweet.price}</span>
                <span style={styles.quantity}>
                  Stock: {sweet.quantity}
                </span>
              </div>
              <button
                className="btn btn-primary"
                style={styles.buyBtn}
                onClick={() => handlePurchase(sweet.id, sweet.name)}
                disabled={sweet.quantity === 0}
              >
                {sweet.quantity === 0 ? 'Out of Stock' : 'Buy Now'}
              </button>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

const styles = {
  title: {
    textAlign: 'center',
    marginBottom: '30px',
    color: '#333',
    fontSize: '36px',
  },
  filterCard: {
    marginBottom: '30px',
  },
  filters: {
    display: 'flex',
    gap: '15px',
    flexWrap: 'wrap',
  },
  searchBox: {
    flex: 1,
    minWidth: '200px',
  },
  searchInput: {
    width: '100%',
    padding: '10px',
    border: '1px solid #ddd',
    borderRadius: '4px',
    fontSize: '14px',
  },
  categorySelect: {
    padding: '10px',
    border: '1px solid #ddd',
    borderRadius: '4px',
    fontSize: '14px',
    minWidth: '150px',
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))',
    gap: '20px',
  },
  sweetCard: {
    display: 'flex',
    flexDirection: 'column',
    height: '100%',
  },
  sweetName: {
    fontSize: '20px',
    marginBottom: '10px',
    color: '#333',
  },
  category: {
    fontSize: '12px',
    color: '#666',
    textTransform: 'uppercase',
    marginBottom: '10px',
    fontWeight: '600',
  },
  description: {
    color: '#666',
    fontSize: '14px',
    marginBottom: '15px',
    flexGrow: 1,
  },
  details: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '15px',
  },
  price: {
    fontSize: '24px',
    fontWeight: 'bold',
    color: '#4CAF50',
  },
  quantity: {
    fontSize: '14px',
    color: '#666',
  },
  buyBtn: {
    width: '100%',
  },
  noResults: {
    textAlign: 'center',
    gridColumn: '1 / -1',
    padding: '40px',
    color: '#666',
    fontSize: '18px',
  },
};

export default SweetList;
