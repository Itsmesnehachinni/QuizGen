import React, { useEffect, useState } from 'react';
import * as yup from 'yup';
import { useFormik } from 'formik';
import { useNavigate } from "react-router-dom";
import { Modal, Button, Form } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './UpdateQuestion.css';

const UpdateMedicine = ({ showUpdateModal, setShowUpdateModal,pharmaMedicineId , setRefreshData }) => {
  console.log("pharmacyMedicineId", pharmaMedicineId);
  const navigate = useNavigate();
  const [medicineData, setMedicineData] = useState({
    medicineName: '',
    manfacturingDate: '',
    expiryDate: '',
    manufacturingCompany: '',
    medicineDescription: '',
    stock: '',
    price: '',
  });
  const today = new Date().toISOString().split('T')[0];

  const fetchData = () => {
    return fetch(`http://localhost:3000/api/v1/pharma-service/medicine/getMedicineById?pharmacyMedicineId=${pharmaMedicineId}`)
      .then(response => response.json())
      .then(responseData => setMedicineData(responseData.data))
      .catch(error => {
        console.error('Error fetching medicine details:', error);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  const formik = useFormik({
    initialValues: medicineData,
    enableReinitialize: true,
    validationSchema: yup.object({
      manfacturingDate: yup.string().required('Manufacturing Date cannot be empty'),
      expiryDate: yup.string().required('Expiry Date cannot be empty'),
      stock: yup.string()
        .matches(/^[1-9]\d*$/, 'Stock must be a number and greater than 0')
        .required('Stock Count cannot be empty'),
      price: yup.number()
        .positive('Price must be greater than 0')
        .min(0.01, 'Price must be at least 0.01')
        .required("Price cannot be empty"),
    }),
    onSubmit: values => {
      fetch(`http://localhost:3000/api/v1/pharma-service/medicine/editMedicine`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
      })
        .then(response => {
          if (!response.ok) {
            alert("Failed to update medicine details");
            throw new Error(response.status);
          }
          handleUpdateModalDialogue(false)
          navigate("/pharmacy-dashboard")
        })
        
        .catch(error => {
          console.log('Error occurred while updating medicine details: ' + error);
        });
    }
  });

  if (!medicineData) {
    return <div>Loading...</div>;
  }

  const handleUpdateModalDialogue = (showModalVal) => {
    setShowUpdateModal(showModalVal);
    setRefreshData(true);
  };

  return (
    <div className = 'update-medicine'>
      <div className='update-medicine-modal-container modal-lg'>
        <Modal 
        show={showUpdateModal} 
        onHide={() => handleUpdateModalDialogue(false)} 
        backdrop="static"
        style={{ backgroundColor: '#f0f8ff' }}
        >
          <Modal.Header closeButton>
            <Modal.Title>Update Question</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form onSubmit={formik.handleSubmit}>
              <Form.Group className="mb-3">
                <Form.Label>Question Name</Form.Label>
                <Form.Control
                  id="medicineName"
                  name="medicineName"
                  type="text"
                  value={formik.values.medicineName}
                  readOnly
                />
              </Form.Group>

              {/* <Form.Group className="mb-3">
                <Form.Label>Manufacturing Date*</Form.Label>
                <Form.Control
                  id="manfacturingDate"
                  name="manfacturingDate"
                  type="date"
                  value={formik.values.manfacturingDate}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  max={today}
                />
                {formik.errors.manfacturingDate && formik.touched.manfacturingDate ? (
                  <span className="text-danger">{formik.errors.manfacturingDate}</span>
                ) : null}
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Expiry Date*</Form.Label>
                <Form.Control
                  id="expiryDate"
                  name="expiryDate"
                  type="date"
                  value={formik.values.expiryDate}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  min={today}
                />
                {formik.errors.expiryDate && formik.touched.expiryDate ? (
                  <span className="text-danger">{formik.errors.expiryDate}</span>
                ) : null}
              </Form.Group> */}

              <Form.Group className="mb-3">
                <Form.Label>Option 1*</Form.Label>
                <Form.Control
                  id="stock"
                  name="stock"
                  type="text"
                  value={formik.values.stock}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                />
                {formik.errors.stock && formik.touched.stock ? (
                  <span className="text-danger">{formik.errors.stock}</span>
                ) : null}
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Option 2*</Form.Label>
                <Form.Control
                  id="price"
                  name="price"
                  type="text"
                  value={formik.values.price}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                />
                {formik.errors.price && formik.touched.price ? (
                  <span className="text-danger">{formik.errors.price}</span>
                ) : null}
              </Form.Group>

              <Modal.Footer>
                <Button variant="secondary" onClick={() => handleUpdateModalDialogue(false)}>
                  Close
                </Button>
                <Button variant="primary" type="submit">
                  Update
                </Button>
              </Modal.Footer>
            </Form>
          </Modal.Body>
        </Modal>
      </div>
    </div>
  );
};

export default UpdateMedicine;
