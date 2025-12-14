import React, { useState, useEffect } from 'react';
import { sweetAPI } from '../services/api';

const AdminPanel = () => {
  const [sweets, setSweets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [editingSweet, setEditingSweet] = useState(null);
  const [formData, setFormData] = useState({
    name: '',
    category: 'CHOCOLATE',
    price: '',
    quantity: '',
    description: '',
  });

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

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      if (editingSweet) {
        await sweetAPI.update(editingSweet.id, formData);
        setSuccess('Sweet updated successfully!');
      } else {
        await sweetAPI.create(formData);
        setSuccess('Sweet created successfully!');
      }
      
      setShowModal(false);
      setEditingSweet(null);
      setFormData({
        name: '',
        category: 'CHOCOLATE',
        price: '',
        quantity: '',
        description: '',
      });
      fetchSweets();
      setTimeout(() => setSuccess(''), 3000);
    } catch (err) {
      setError(err.response?.data?.message || 'Operation failed');
      setTimeout(() => setError(''), 3000);
    }
  };

  const handleEdit = (sweet) => {
    setEditingSweet(sweet);
    setFormData({
      name: sweet.name,
      category: sweet.category,
      price: sweet.price,
      quantity: sweet.quantity,
      description: sweet.description || '',
    });
    setShowModal(true);
  };

  const handleDelete = async (id, name) => {
    if (!window.confirm(`Are you sure you want to delete ${name}?`)) return;

    try {
      await sweetAPI.delete(id);
      setSuccess('Sweet deleted successfully!');
      fetchSweets();
      setTimeout(() => setSuccess(''), 3000);
    } catch (err) {
      setError(err.response?.data?.message || 'Delete failed');
      setTimeout(() => setError(''), 3000);
    }
  };

  const handleRestock = async (id, name) => {
    const quantity = prompt(`How many ${name} to add to stock?`, '10');
    if (!quantity || isNaN(quantity) || quantity < 1) return;

    try {
      await sweetAPI.restock(id, parseInt(quantity));
      setSuccess(`Successfully restocked ${quantity} ${name}!`);
      fetchSweets();
      setTimeout(() => setSuccess(''), 3000);
    } catch (err) {
      setError(err.response?.data?.message || 'Restock failed');
      setTimeout(() => setError(''), 3000);
    }
  };

  const openAddModal = () => {
    setEditingSweet(null);
    setFormData({
      name: '',
      category: 'CHOCOLATE',
      price: '',
      quantity: '',
      description: '',
    });
    setShowModal(true);
  };

  if (loading) {
    return <div className="spinner"></div>;
  }

  return (
    <div className="container">
      <div style={styles.header}>
        <h1 style={styles.title}>Admin Panel</h1>
        <button className="btn btn-primary" onClick={openAddModal}>
          + Add New Sweet
        </button>
      </div>

      {error && <div className="alert alert-error">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      <div className="card">
        <table style={styles.table}>
          <thead>
            <tr>
              <th style={styles.th}>Name</th>
              <th style={styles.th}>Category</th>
              <th style={styles.th}>Price</th>
              <th style={styles.th}>Quantity</th>
              <th style={styles.th}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {sweets.map(sweet => (
              <tr key={sweet.id}>
                <td style={styles.td}>{sweet.name}</td>
                <td style={styles.td}>{sweet.category}</td>
                <td style={styles.td}>₹{sweet.price}</td>
                <td style={styles.td}>
                  <span style={sweet.quantity < 10 ? styles.lowStock : {}}>
                    {sweet.quantity}
                  </span>
                </td>
                <td style={styles.td}>
                  <div style={styles.actions}>
                    <button
                      className="btn btn-secondary"
                      onClick={() => handleEdit(sweet)}
                      style={styles.actionBtn}
                    >
                      Edit
                    </button>
                    <button
                      className="btn btn-warning"
                      onClick={() => handleRestock(sweet.id, sweet.name)}
                      style={styles.actionBtn}
                    >
                      Restock
                    </button>
                    <button
                      className="btn btn-danger"
                      onClick={() => handleDelete(sweet.id, sweet.name)}
                      style={styles.actionBtn}
                    >
                      Delete
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {showModal && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2>{editingSweet ? 'Edit Sweet' : 'Add New Sweet'}</h2>
              <button className="modal-close" onClick={() => setShowModal(false)}>
                ×
              </button>
            </div>

            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Name *</label>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleInputChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Category *</label>
                <select
                  name="category"
                  value={formData.category}
                  onChange={handleInputChange}
                  required
                >
                  {categories.map(cat => (
                    <option key={cat} value={cat}>{cat}</option>
                  ))}
                </select>
              </div>

              <div className="form-group">
                <label>Price (₹) *</label>
                <input
                  type="number"
                  name="price"
                  value={formData.price}
                  onChange={handleInputChange}
                  step="0.01"
                  min="0"
                  required
                />
              </div>

              <div className="form-group">
                <label>Quantity *</label>
                <input
                  type="number"
                  name="quantity"
                  value={formData.quantity}
                  onChange={handleInputChange}
                  min="0"
                  required
                />
              </div>

              <div className="form-group">
                <label>Description</label>
                <textarea
                  name="description"
                  value={formData.description}
                  onChange={handleInputChange}
                  rows="3"
                  maxLength="1000"
                />
              </div>

              <div style={styles.modalActions}>
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => setShowModal(false)}
                >
                  Cancel
                </button>
                <button type="submit" className="btn btn-primary">
                  {editingSweet ? 'Update' : 'Create'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

const styles = {
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '30px',
  },
  title: {
    color: '#333',
    fontSize: '32px',
    margin: 0,
  },
  table: {
    width: '100%',
    borderCollapse: 'collapse',
  },
  th: {
    textAlign: 'left',
    padding: '12px',
    borderBottom: '2px solid #ddd',
    fontWeight: '600',
    color: '#333',
  },
  td: {
    padding: '12px',
    borderBottom: '1px solid #eee',
  },
  actions: {
    display: 'flex',
    gap: '8px',
    flexWrap: 'wrap',
  },
  actionBtn: {
    fontSize: '12px',
    padding: '6px 12px',
  },
  lowStock: {
    color: '#f44336',
    fontWeight: 'bold',
  },
  modalActions: {
    display: 'flex',
    justifyContent: 'flex-end',
    gap: '10px',
    marginTop: '20px',
  },
};

export default AdminPanel;
