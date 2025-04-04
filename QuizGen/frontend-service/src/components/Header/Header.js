import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from "react-router-dom";


const Header = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const isLoggedIn = useSelector(state => state.isLoggedIn);
    const role = useSelector(state => state.role);
    
    const customerId = useSelector((state) => state.customerId);



    const handleLogout = async () => {
        if (role == 'CUSTOMER') {
            try {
                const deleteResponse = await axios.delete(`http://localhost:3000/api/v1/order-service/cart/deleteAllCartItemsByCustomerId`
                    , {
                        params: {
                            customerId: customerId,
                        }
                    });
                if (deleteResponse.status === 200) {
                    dispatch({ type: 'SET_LOGOUT' });
                    navigate("/logout");
                }
                else {
                    alert("Error while logout, please try again");
                }
            } catch (err) {
                console.error("Error while logout:", err);
            }
        } else {
            dispatch({ type: 'SET_LOGOUT' });
            navigate("/logout");
        }

    }

    return (
        <header>
            <div className="header-banner">
                {/* Left-aligned Logo and Brand */}
                <div className="logo-brand">
                    <img className="logo" src={`${process.env.PUBLIC_URL}/images/logo.png`} alt="CareDrop Logo" />
                    <span className="brand-name">Quiz Generator</span>
                </div>

                {/* Center-aligned Navigation Menu */}
                <nav className="nav-menu">
                    <Link to="/" className="nav-item"><i className="fas fa-home"></i> Home</Link>
                    {isLoggedIn && (<Link 
                    to= { role == 'CUSTOMER' ? "/userDashboard/progressTracking" : "/admin-dashboard"}
                    className="nav-item"><i className="fas fa-tachometer-alt"></i> Dashboard</Link>)}
                    <Link to="/contact" className="nav-item"><i className="fas fa-phone-alt"></i> Contact Us</Link>
                </nav>



                {/* Right-aligned Buttons */}
                <div className="header-buttons">

                    <Link to="/user-register" className="data-insights">
                        {/* <i className="fas fa-chart-line"></i>  */}
                        Sign Up
                    </Link>

                    {isLoggedIn ? (
                        // Show Logout button if logged in
                        <Link to="/logout" className="logout-btn" 
                        onClick={handleLogout}>
                            <i className="fas fa-sign-out-alt"></i> Logout
                        </Link>
                    ) : (
                        // Show Login button if not logged in
                        <Link to="/login" className="login-btn">
                            {/* <i className="fas fa-sign-in-alt"></i>  */}
                            Login
                        </Link>
                    )}
                </div>
            </div>
        </header>
    );
};

export default Header;
