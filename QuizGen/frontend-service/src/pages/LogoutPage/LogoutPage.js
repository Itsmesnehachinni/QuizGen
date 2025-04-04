import React from 'react';
import { Link } from 'react-router-dom';
import './LogoutPage.css'; // Make sure to add your styles here


const LogoutPage = () => {
    
    return (
        <div className="logout-page">
            <div className="logout-page-img-container">
                <img className="logout-page-img" src={`${process.env.PUBLIC_URL}/images/logout.png`} />
            </div>
            <div className="logout-message">
                <h1>Thanks for quizzing! </h1>
                <p>Log in Again to start fresh next time</p>
                <div className="cta-buttons">
                    <Link to="/">
                        <button className="cta-button home-btn">Go to Home</button>
                    </Link>
                    <Link to="/login">
                        <button className="cta-button login-btn">Login Again</button>
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default LogoutPage;
