import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Button,
  Typography,
  Box,
  IconButton,
  Card,
  CardContent,
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import AddIcon from '@mui/icons-material/Add';
import RemoveIcon from '@mui/icons-material/Remove';
import { useLocation } from 'react-router-dom';
import './UserQuiz.css';
import { useNavigate } from "react-router-dom";
import { useSelector } from 'react-redux';



const PharmacyMedicines = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const pharmacyId = queryParams.get('pharmacyId');

  const [expanded, setExpanded] = useState(null); // Track the currently expanded accordion


  const [quantities, setQuantities] = useState({});
  const [medicines, setMedicines] = useState([]);
  const customerId = useSelector((state) => state.customerId);


  useEffect(() => {
    if (pharmacyId) {
      fetch(`http://localhost:3000/api/v1/pharma-service/medicine/getAllMedicinesByPharmacyId?pharmacyId=${pharmacyId}`)
        .then(res => res.json())
        .then(response => {
          console.log("API Response :", response.data.pharmacyMedicines)
          if (response.data.pharmacyMedicines.length === 0) {
            alert("There is no medicine available in the pharmacy");
          }
          setMedicines(response.data.pharmacyMedicines);
        })
        .catch(error => console.error('Error fetching medicines:', error));
    }
  }, [pharmacyId]);

  const handleQuantityChange = (id, change) => {
    setQuantities((prev) => {
      const currentQty = prev[id] || 0;
      const newQty = Math.max(0, currentQty + change);
      return { ...prev, [id]: newQty };
    });
  };

  function handleStartQuiz() {
    navigate(`/performQuiz`)
    
}

  const handleAddToCart = () => {
    // Construct the cart items from the quantities
    console.log("keys:" + Object.keys(quantities))
    console.log("quantities" + JSON.stringify(quantities))
    const cartItems = Object.keys(quantities)
      .filter(key => quantities[key] > 0) // Filter items with quantity > 0
      .map(key => ({
        pharmaMedicineId: key,
        quantity: quantities[key],
      }));

    if (cartItems.length > 0) {
      const payload = {
        customerId: customerId,
        cartItems: cartItems,
      };

      // Send the payload to the backend
      fetch('http://localhost:3000/api/v1/order-service/cart/addCart', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      })
        .then(res => {
          if (res.ok) {
            //Add navigation to cart items page ***
            return res.json();
          }
          throw new Error('Network response was not ok.');
        })
        .then(data => {
          console.log('Success:', data);
          // Optionally, reset quantities or show a success message
          setQuantities({});
          navigate(`/cart-details`)
        })
        .catch(error => {
          console.error('Error adding to cart:', error);
          alert("Error adding to cart");
          navigate(`/userDashboard/quiz?pharmacyId=${pharmacyId}`)

        });
    } else {
      alert("Please add items in the cart to proceed further")
      console.log('No items in cart to add.');
    }
  };

  /** function that allows accordian to expand only one at a time */
  const handleToggle = (pharmaMedicineId) => {
    setExpanded(prevExpanded => (prevExpanded === pharmaMedicineId ? null : pharmaMedicineId)); // Toggle the selected accordion
  };

  return (
    <div className="medicine-container">
      <div className="container mt-4">
        <h1 className="text-center my-4 title-text">Quiz Dashboard</h1>
        {medicines.map((medicine) => (
          <Card className="medicine-card m-3" key={medicine.pharmaMedicineId}>
            <Accordion expanded={expanded === medicine.pharmaMedicineId} key={medicine.pharmaMedicineId}>
              <AccordionSummary
                expandIcon={
                  <IconButton onClick={() => handleToggle(medicine.pharmaMedicineId)}>
                    <ExpandMoreIcon />
                  </IconButton>
                }
                className="accordion-summary"
                disableGutters
                onClick={(e) => e.stopPropagation()}
              >
                <div className="d-flex justify-content-between align-items-center w-100">
                  <Typography variant="h6">{medicine.medicineName}</Typography>
                  <Box className="quantity-control mx-3">
                    <Typography variant="body1" sx={{ color: 'blue' }} className="mr-2"> Qty:</Typography> {/* Quantity label */}
                    <IconButton
                      onClick={() => handleQuantityChange(medicine.pharmaMedicineId, -1)}
                      disabled={(quantities[medicine.pharmaMedicineId] || 0) <= 0}
                    >
                      <RemoveIcon className="remove-icon" />
                    </IconButton>
                    <Typography>{quantities[medicine.pharmaMedicineId] || 0}</Typography>
                    <IconButton
                      onClick={() => handleQuantityChange(medicine.pharmaMedicineId, 1)}
                      disabled={(quantities[medicine.pharmaMedicineId] || 0) >= medicine.stock}
                    >
                      <AddIcon className="add-icon" />
                    </IconButton>
                  </Box>
                </div>
              </AccordionSummary>
              <AccordionDetails className="accordion-details">
                <CardContent className='text-left'>
                  <Typography>
                    <strong>Manufacturing Date:</strong> {medicine.manufacturingDate}
                  </Typography>
                  <Typography>
                    <strong>Expiry Date:</strong> {medicine.expiryDate}
                  </Typography>
                  <Typography>
                    <strong>Manufacturing Company:</strong> {medicine.manufacturingCompany}
                  </Typography>
                  <Typography>
                    <strong>Description:</strong> {medicine.medicineDescription}
                  </Typography>
                  <Typography>
                    <strong>Price:</strong> ${medicine.price}
                  </Typography>
                </CardContent>
              </AccordionDetails>
            </Accordion>
          </Card>
        ))}
        <div className="mt-3 Justify-content">

          <button class="btn btn-primary  mb-1 px-1 me-4 btn-lg mt-2 btn-txt"
            type="button"
            onClick={handleStartQuiz}>
               Start Quiz
               </button>

        </div>
      </div>
    </div>
  );
};

export default PharmacyMedicines;