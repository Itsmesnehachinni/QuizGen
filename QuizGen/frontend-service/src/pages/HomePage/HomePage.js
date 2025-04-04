import React, { useEffect, useState } from 'react';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css'; // Import carousel styles
import { Link } from 'react-router-dom';
import { Tooltip } from 'react-tooltip'; // Correct import
import './HomePage.css';

const HomePage = () => {
    const [currentSlide, setCurrentSlide] = useState(0);
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    // Check for session ID (for now, from localStorage)
    useEffect(() => {
        setIsLoggedIn(false);
    }, []);

    const handleCarouselChange = (index) => {
        setCurrentSlide(index);
    };

    return (
        <div className="homepage-carousel-container">
            <Carousel
                showArrows={true}
                autoPlay={true}
                infiniteLoop={true}
                showThumbs={false}
                showStatus={false}
                onChange={handleCarouselChange}
                className="homepage-carousel"
            >
                {/* Slide 1 */}
                <div>        
                    <img src={`${process.env.PUBLIC_URL}/images/background7.png`} alt="Slide 1" />
                </div>
            </Carousel>
        </div>
    );
};

export default HomePage;
