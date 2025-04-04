import React from 'react';
import './ContactPage.css';

const ContactPage = () => {
    return (
        <div className="contact-page">
            <div className="left-column">
                <img src={`${process.env.PUBLIC_URL}/images/contactus.png`} alt="Contact Illustration" className="contact-image" />
            </div>
            <div className="right-column contact-container">
                <h1>Contact Us</h1>
                <p> Feel free to reach out to us. We're here to help!</p>

                <div className="contact-info">
                    <div className="contact-item">
                        <h2>Email</h2>
                        <p>support@quizgenarator.com</p>
                    </div>
                    <div className="contact-item">
                        <h2>Phone</h2>
                        <p>+1 234-567-8900</p>
                    </div>
                    <div className="contact-item">
                        <h2>Address</h2>
                        <p>Harrisburg, PA</p>
                    </div>
                </div>

                <form className="contact-form">
                    <h2>Send us a message</h2>
                    <div className="form-group">
                        <label htmlFor="name">Your Name</label>
                        <input type="text" id="name" placeholder="Enter your name" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="email">Your Email</label>
                        <input type="email" id="email" placeholder="Enter your email" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="message">Message</label>
                        <textarea id="message" placeholder="Enter your message"></textarea>
                    </div>
                    <button type="submit">Send Message</button>
                </form>
            </div>
        </div>
    );
};

export default ContactPage;
