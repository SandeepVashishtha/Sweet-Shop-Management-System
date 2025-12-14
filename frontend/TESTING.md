# Testing Guide - Sweet Shop Management System

## Quick Start Testing

The application is now running at: **http://localhost:5174**

## Test Scenarios

### 1. User Registration & Login

#### Test Registration
1. Click on "Register here" link on the login page
2. Fill in the registration form:
   - Username: `testuser1`
   - Email: `testuser1@example.com`
   - Password: `password123`
   - Confirm Password: `password123`
3. Click "Register"
4. You should be automatically logged in and redirected to the sweet shop

#### Test Login
1. If already registered, go to the login page
2. Enter credentials:
   - Username: `testuser1`
   - Password: `password123`
3. Click "Login"
4. You should be redirected to the sweet shop catalog

### 2. Sweet Shop Features (Regular User)

#### Browse Sweets
1. After logging in, you should see the sweet catalog
2. All available sweets should be displayed in a grid
3. Each sweet card shows:
   - Name
   - Category
   - Description
   - Price
   - Stock quantity
   - Buy button

#### Search Functionality
1. Type in the search box (e.g., "chocolate")
2. The list should filter in real-time
3. Try searching by name or description

#### Category Filter
1. Select a category from the dropdown (e.g., "CHOCOLATE")
2. Only sweets from that category should display
3. Select "All Categories" to show all sweets again

#### Purchase Sweet
1. Find a sweet with stock > 0
2. Click the "Buy Now" button
3. Enter the quantity you want to purchase
4. Click OK
5. You should see a success message
6. The quantity should decrease
7. If quantity reaches 0, the button should show "Out of Stock"

### 3. Admin Panel Features (Admin User Only)

#### Access Admin Panel
1. Login as an admin user
2. You should see "Admin Panel" link in the navbar
3. Click on "Admin Panel"

**Note**: You need an admin account from the backend. Regular users won't see this option.

#### Add New Sweet
1. In the Admin Panel, click "+ Add New Sweet"
2. Fill in the form:
   - Name: `Milk Chocolate Bar`
   - Category: `CHOCOLATE`
   - Price: `150`
   - Quantity: `50`
   - Description: `Smooth and creamy milk chocolate`
3. Click "Create"
4. The new sweet should appear in the table

#### Edit Sweet
1. Find a sweet in the table
2. Click the "Edit" button
3. Modify any field (e.g., change price to `175`)
4. Click "Update"
5. The sweet details should be updated

#### Restock Sweet
1. Find a sweet with low quantity
2. Click the "Restock" button
3. Enter the quantity to add (e.g., `20`)
4. Click OK
5. The quantity should increase

#### Delete Sweet
1. Find a sweet to delete
2. Click the "Delete" button
3. Confirm the deletion
4. The sweet should be removed from the table

### 4. Navigation & Authentication

#### Test Protected Routes
1. Logout from the application
2. Try to access the main shop or admin panel directly
3. You should be redirected to the login page

#### Test Role-Based Access
1. Login as a regular user
2. Try to access `/admin` directly in the URL
3. You should be redirected to the main shop (no access)

#### Test Logout
1. Click the "Logout" button in the navbar
2. You should be redirected to the login page
3. Try to go back - you should remain logged out

## Expected Behavior

### ✅ Working Features
- User registration with validation
- User login with JWT authentication
- Protected routes (must be logged in)
- Sweet catalog with search and filter
- Real-time inventory updates
- Purchase functionality
- Admin panel (for admin users only)
- CRUD operations for sweets
- Restock functionality
- Responsive design (works on mobile, tablet, desktop)

### 🎨 UI/UX Features
- Loading spinners during API calls
- Success/error messages
- Disabled buttons when appropriate
- Smooth transitions and animations
- Responsive grid layout
- Modern, clean design
- Low stock indicators (red text when < 10)

## Common Test Cases

### Positive Tests
- ✅ Register with valid credentials
- ✅ Login with correct username/password
- ✅ Search for existing sweets
- ✅ Filter by category
- ✅ Purchase sweets with sufficient stock
- ✅ Admin can add new sweets
- ✅ Admin can edit sweet details
- ✅ Admin can delete sweets
- ✅ Admin can restock inventory

### Negative Tests
- ⚠️ Register with mismatched passwords
- ⚠️ Login with wrong credentials
- ⚠️ Try to purchase more than available stock
- ⚠️ Try to access admin panel as regular user
- ⚠️ Try to access shop while logged out

## Browser Console

Open the browser console (F12) to see:
- Network requests to the backend
- Any error messages
- Authentication token being sent

## Troubleshooting

### Issue: Can't login
- Check that the backend is running
- Verify the backend URL in `src/services/api.js`
- Check browser console for errors

### Issue: Admin panel not visible
- Ensure you're logged in as an admin user
- Regular users cannot access the admin panel

### Issue: Purchase fails
- Check that there's sufficient stock
- Verify the backend is processing the request
- Check browser console for error messages

### Issue: Sweets not loading
- Verify backend connection
- Check browser console for network errors
- Ensure you're logged in

## Performance Testing

1. **Load Time**: Page should load within 2-3 seconds
2. **Search**: Should filter instantly as you type
3. **API Calls**: Should complete within 1-2 seconds
4. **Animations**: Should be smooth (no lag)

## Mobile Testing

1. Open the app on a mobile device or use browser dev tools
2. Switch to mobile view (responsive mode)
3. Test all features:
   - Login/Register forms should be readable
   - Sweets should display in 1-2 columns
   - Buttons should be easily tappable
   - Forms should not zoom when focused

## Automated Testing Checklist

- [ ] User can register
- [ ] User can login
- [ ] User can view sweets
- [ ] User can search sweets
- [ ] User can filter by category
- [ ] User can purchase sweets
- [ ] Admin can access admin panel
- [ ] Admin can add sweets
- [ ] Admin can edit sweets
- [ ] Admin can delete sweets
- [ ] Admin can restock sweets
- [ ] User can logout
- [ ] Protected routes work
- [ ] Role-based access works

## Backend Endpoints Being Tested

```
✅ POST /api/auth/register
✅ POST /api/auth/login
✅ GET  /api/sweets
✅ GET  /api/sweets/:id
✅ POST /api/sweets (Admin)
✅ PUT  /api/sweets/:id (Admin)
✅ DELETE /api/sweets/:id (Admin)
✅ POST /api/sweets/:id/purchase
✅ POST /api/sweets/:id/restock (Admin)
```

## Test Data Suggestions

### Sample Sweets to Add
1. **Dark Chocolate**: Category: CHOCOLATE, Price: 200, Qty: 30
2. **Gummy Bears**: Category: GUMMY, Price: 50, Qty: 100
3. **Sugar Cookie**: Category: COOKIE, Price: 30, Qty: 75
4. **Birthday Cake**: Category: CAKE, Price: 500, Qty: 10
5. **Lollipop**: Category: CANDY, Price: 20, Qty: 200

## Success Criteria

✅ All features work as expected
✅ UI is responsive and looks good
✅ No console errors
✅ API calls are successful
✅ Authentication works correctly
✅ Admin features are protected
✅ User experience is smooth

---

**Happy Testing! 🍬**
