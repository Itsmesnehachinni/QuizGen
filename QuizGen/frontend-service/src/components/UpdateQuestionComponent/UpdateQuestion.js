import React, { useEffect, useState } from 'react';
import * as yup from 'yup';
import { useFormik } from 'formik';
import { useNavigate } from "react-router-dom";
import { Modal, Button, Form } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './UpdateQuestion.css';

const UpdateQuestion = ({ showUpdateModal, setShowUpdateModal, updatedQuestionId, setRefreshData }) => {
  const navigate = useNavigate();
  const [questionData, setQuestionData] = useState({
    questionDescription: '',
    optionOne: '',
    optionTwo: '',
    optionThree: '',
    optionFour: '',
    correctAnswer: '',
    level: ''
  });

  const fetchData = () => {
    console.log("UpdateQuestion :: showUpdateModal",showUpdateModal)
    console.log("UpdateQuestion: question Id", updatedQuestionId);
    return fetch(`http://localhost:7070/questions/${updatedQuestionId}`)
      .then(response => response.json())
      //uncomment the below line after backend integration
      // .then(responseData => setQuestionData(responseData.data))
      //comment the below line after backend integration
      .then(responseData => setQuestionData(responseData))
      .catch(error => {
        console.error('Error fetching medicine details:', error);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  const formik = useFormik({
    initialValues: questionData,
    enableReinitialize: true,
    validationSchema: yup.object({
      optionOne: yup.string().required('Manufacturing Date cannot be empty'),
      optionTwo: yup.string().required('Option Two cannot be empty'),
      correctAnswer: yup.string().required('Correct Answer cannot be empty'),
    }),
    onSubmit: values => {
      fetch(`http://localhost:7070/questions/${updatedQuestionId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
      })
        .then(response => {
          if (!response.ok) {
            alert("Failed to update question details");
            throw new Error(response.status);
          }
          handleUpdateModalDialogue(false)
          navigate("/admin-dashboard")
        })
        
        .catch(error => {
          console.log('Error occurred while updating question details: ' + error);
        });
    }
  });

  if (!questionData) {
    console.log("questionData::",questionData)
    return <div>Loading...</div>;
  }

  const handleUpdateModalDialogue = (showModalVal) => {
    console.log("Update:: showModalVal:",showModalVal)
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
                <Form.Label>Question Description</Form.Label>
                <Form.Control
                  id="questionDescription"
                  name="questionDescription"
                  type="text"
                  value={formik.values.questionDescription}
                  readOnly
                />
              </Form.Group>          

              <Form.Group className="mb-3">
                <Form.Label>Option One*</Form.Label>
                <Form.Control
                  id="optionOne"
                  name="optionOne"
                  type="text"
                  value={formik.values.optionOne}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                />
                {formik.errors.optionOne && formik.touched.optionOne ? (
                  <span className="text-danger">{formik.errors.optionOne}</span>
                ) : null}
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Option Two*</Form.Label>
                <Form.Control
                  id="optionTwo"
                  name="optionTwo"
                  type="text"
                  value={formik.values.optionTwo}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                />
                {formik.errors.optionTwo && formik.touched.optionTwo ? (
                  <span className="text-danger">{formik.errors.optionTwo}</span>
                ) : null}
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Option Three</Form.Label>
                <Form.Control
                  id="optionThree"
                  name="optionThree"
                  type="text"
                  value={formik.values.optionThree}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                />
                {formik.errors.optionThree && formik.touched.optionThree ? (
                  <span className="text-danger">{formik.errors.optionThree}</span>
                ) : null}
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Option Four</Form.Label>
                <Form.Control
                  id="optionFour"
                  name="optionFour"
                  type="text"
                  value={formik.values.optionFour}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                />
                {formik.errors.optionFour && formik.touched.optionFour ? (
                  <span className="text-danger">{formik.errors.optionFour}</span>
                ) : null}
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Correct Answer*</Form.Label>
                <Form.Control
                  id="correctAnswer"
                  name="correctAnswer"
                  type="text"
                  value={formik.values.correctAnswer}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                />
                {formik.errors.correctAnswer && formik.touched.correctAnswer ? (
                  <span className="text-danger">{formik.errors.correctAnswer}</span>
                ) : null}
              </Form.Group>

              <Form.Group className="mb-3">
                <Form.Label>Level</Form.Label>
                <Form.Control
                  id="level"
                  name="level"
                  type="text"
                  value={formik.values.level}
                  readOnly
                />
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

export default UpdateQuestion;
