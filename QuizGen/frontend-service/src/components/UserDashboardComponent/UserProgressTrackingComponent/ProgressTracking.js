import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import './ProgressTracking.css'; 
import { FaPhone, FaMapMarkerAlt, FaEnvelope } from 'react-icons/fa'; 
import Typography from '@mui/material/Typography';
import { useNavigate } from "react-router-dom";
import React from 'react'
import Form from 'react-bootstrap/Form';




function PharmaciesComponent() {
    const [pharmacies, setPharmacies] = React.useState([]);
    const [filteredPharmacies, setFilteredPharmacies] = React.useState([]);
    const [searchTerm, setSearchTerm] = React.useState('');
    const navigate = useNavigate();

    React.useEffect(() => {
        fetch('http://localhost:3000/api/v1/pharma-service/pharma/getAllPharmacies')
            .then(res => res.json())
            .then(data => {
                console.log(data);
                setPharmacies(data.data.pharmacies);
                setFilteredPharmacies(data.data.pharmacies);
            });
    }, [])

    function showPharmacyMedicines(pharmacyId) {
        navigate(`/performQuiz`)
        // navigate(`/customerDashboard/medicines`)

    }

    // Function to handle search input change
    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            handleSearch(event);
        }
    };

    // Filter pharmacies based on search term
    function handleSearch() {
        console.log("searchTerm:" + searchTerm);
        if (searchTerm === '' || searchTerm === null) {
            setFilteredPharmacies(pharmacies)
        }
        else {
            // setFilteredPharmacies(
            //     pharmacies.filter(pharmacy =>
            //         pharmacy.medicines.some(medicine =>
            //             medicine.toLowerCase().includes(searchTerm.toLowerCase())
            //         )
            //     )
            // )
            const filtered = [];

            pharmacies.forEach(pharmacy => {
                if (pharmacy.medicines.some(medicine => 
                    // medicine.toLowerCase().includes(searchTerm.toLowerCase()) //contains match
                    medicine.toLowerCase()===(searchTerm.toLowerCase()) //contains match

                )) {
                    filtered.push(pharmacy);
                }
            });
            setFilteredPharmacies(filtered);
        }
    }

    return (
        <div class="customer-dashboard">
            <div class="search-bar">
                <Row className="mb-4">
                    <Col xs={12} className="d-flex justify-content-end align-items-center">
                        <Form.Control
                            // type="text"
                            placeholder="Search for a medicine..."
                            value={searchTerm}
                            onChange={handleSearchChange}
                            onKeyDown={handleKeyPress} // Add this line to handle Enter key
                            style={{
                                width: '375px',
                                height: '45px',
                                marginRight: '10px',
                                /*backgroundColor :'lightskyblue'*/
                            }} // Adjust the width as needed
                        />
                        <Button onClick={handleSearch} variant="primary">
                            Search
                        </Button>
                    </Col>
                </Row>
            </div>



            <div className='pharmacy-card-container'>
                <Row className="justify-content-center">
                    {filteredPharmacies.map(pharmacy => (
                        <Col key={pharmacy.pharmacyId} xs={12} sm={6} >
                            <Card className="pharmacy-card">
                                <Card.Header className="text-center">
                                    <h3>{pharmacy.pharmaName}</h3>
                                </Card.Header>
                                <Card.Body>
                                    <Card.Text>
                                        <Typography variant="h5" className="mt-2" style={{ fontSize: '1.25rem' }}>
                                            <strong>License:</strong> {pharmacy.licenseNumber}
                                        </Typography>
                                    </Card.Text>
                                    <Card.Title>Contact Information</Card.Title>
                                    <ListGroup variant="flush">
                                        <ListGroup.Item>
                                            <FaPhone className="me-1" /> {pharmacy.phoneNumber}
                                        </ListGroup.Item>
                                        <ListGroup.Item>
                                            <FaEnvelope className="me-1" /> {pharmacy.emailAddress}
                                        </ListGroup.Item>
                                        <ListGroup.Item>
                                            <FaMapMarkerAlt className="me-1" />
                                            {pharmacy.street}, {pharmacy.city}
                                        </ListGroup.Item>
                                    </ListGroup>

                                    <Button id="view-medicines-btn" 
                                    onClick={() => showPharmacyMedicines(pharmacy.pharmacyId)}  
                                    type="button" 
                                    className="my-4 view-medicines-btn">
                                        View Medicines
                                    </Button>
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            </div>
        </div>

    );
}

export default PharmaciesComponent;
