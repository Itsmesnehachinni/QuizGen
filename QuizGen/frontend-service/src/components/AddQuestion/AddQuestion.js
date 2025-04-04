import React from 'react';
import * as yup from 'yup';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useFormik } from 'formik';
import { useNavigate } from 'react-router-dom';
import { Modal, Button, Form } from 'react-bootstrap';
import './AddQuestion.css'; // Ensure your custom CSS file is imported

const AddMedicine = ({showAddModal, setShowAddModal, setRefreshData,pharmacyId}) => {
  const navigate = useNavigate();
  const today = new Date().toISOString().split('T')[0];

  const formik = useFormik({
    initialValues: {
      medicineName: '',
      manfacturingDate: '',
      expiryDate: '',
      manfacturingCompany: '',
      medicineDescription: '',
      stock: '',
      price: '',
    },
    validationSchema: yup.object({
      medicineName: yup.string().required('Medicine Name cannot be empty'),
      manfacturingDate: yup.string().required('Manufacturing Date cannot be empty'),
      expiryDate: yup.string().required('Expiry Date cannot be empty'),
      manfacturingCompany: yup.string().required('Manufacturing Company cannot be empty'),
      medicineDescription: yup.string().required('Medicine Description cannot be empty'),
      stock: yup.string()
        .matches(/^[1-9]\d*$/, 'Stock must be a number and greater than 0')
        .required('Stock Count cannot be empty'),
      price: yup.number()
        .positive('Price must be greater than 0')
        .min(0.01, 'Price must be at least 0.01')
        .required("Price cannot be empty"),
    }),
    onSubmit: values => {
      console.log("values" + JSON.stringify(values));
      const formData = {
        ...values,
        pharmacyId,
        
      };
      console.log("body:",formData);

      fetch('http://localhost:3000/api/v1/pharma-service/medicine/addMedicine', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      })
        .then(response => {
         
          if (!response.ok) {
            if(response.status == '409'){
              alert("Medicine already exists, you can update details instead of re-adding again")
              handleModalDialogue(false)
            }else{
              alert("Error adding medicine, please try again");
            }              
              throw new Error(response.status);                      
          } 
            return response.json();          
        })
        .then((responseJson) => {
          console.log("responseJson.status",responseJson.status)
          if(responseJson.status === '409') {
            alert(responseJson.message)
          }else{
            alert("Medicine added successfully");
            handleModalDialogue(false)
            navigate("/pharmacy-dashboard")
          }          
        })
        .catch((error) => {
          console.log('Error occurred while adding medicine details: ' + error);
        });
    },
  });

  const handleModalDialogue = (showModalVal) => {
    setShowAddModal(showModalVal);
    setRefreshData(true);
  };

  return (
    <div className='add-medicine-modal-container'>
      <Modal show={showAddModal} 
      onHide={() => handleModalDialogue(false)} 
      backdrop="static"
      size="lg">
        <Modal.Header closeButton>
          <Modal.Title>ADD MEDICINE</Modal.Title>
        </Modal.Header>
        <Form onSubmit={formik.handleSubmit}>
          <Modal.Body>
            {[
              { label: 'Medicine Name*', id: 'medicineName', type: 'text', placeholder: 'Enter Medicine Name' },
              { label: 'Manufacturing Date*', id: 'manfacturingDate', type: 'date', placeholder: '' },
              { label: 'Expiry Date*', id: 'expiryDate', type: 'date', placeholder: '' },
              { label: 'Manufacturing Company*', id: 'manfacturingCompany', type: 'text', placeholder: 'Enter manufacturing company name' },
              { label: 'Medicine Description*', id: 'medicineDescription', type: 'text', placeholder: 'Enter medicine description' },
              { label: 'Stock*', id: 'stock', type: 'text', placeholder: 'Enter available stock count' },
              { label: 'Price*', id: 'price', type: 'text', placeholder: 'Enter MRP' },
            ].map(({ label, id, type, placeholder }) => (
              <Form.Group controlId={id} className="mb-3" key={id}>
                <Form.Label>{label}</Form.Label>
                <Form.Control
                  type={type}
                  placeholder={placeholder}
                  value={formik.values[id]}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  isInvalid={formik.touched[id] && !!formik.errors[id]}
                  className="custom-input" // Custom class for input fields
                  min={id === 'expiryDate' ? today : undefined} // Disable future dates for manufacturing date
                  max={id === 'manfacturingDate' ? today : undefined} // Disable past dates for expiry date
                />
                <Form.Control.Feedback type="invalid">
                  {formik.errors[id]}
                </Form.Control.Feedback>
              </Form.Group>
            ))}
          </Modal.Body>
          <Modal.Footer>
            <Button 
             variant="secondary"
             onClick={() => handleModalDialogue(false)}>
              Close
            </Button>
            <Button type="submit" variant="primary">
              Add
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </div>
  );
};

export default AddMedicine;
