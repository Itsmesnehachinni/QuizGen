import React from 'react';
import * as yup from 'yup';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useFormik } from 'formik';
import { useNavigate } from 'react-router-dom';
import { Modal, Button, Form } from 'react-bootstrap';
import './AddQuestion.css'; // Ensure your custom CSS file is imported
import { v4 as uuidv4 } from 'uuid';

const AddQuestion = ({ showAddModal, setShowAddModal, setRefreshData }) => {
  const navigate = useNavigate();

  const formik = useFormik({
    initialValues: {
      questionDescription: '',
      optionOne: '',
      optionTwo: '',
      optionThree: '',
      optionFour: '',
      correctAnswer: '',
      level: '',
    },
    validationSchema: yup.object({
      questionDescription: yup.string().required('Question Description cannot be empty'),
      optionOne: yup.string().required('Option One cannot be empty'),
      optionTwo: yup.string().required('Option Two cannot be empty'),
      correctAnswer: yup.string().required('Correct Answer cannot be empty'),
      level: yup.string().required('Question difficulty level cannot be empty'),

    }),
    onSubmit: values => {
      console.log("on submit values:" + JSON.stringify(values));
      const questionId = uuidv4();
      const formData = {
        ...values,
        questionId,

      };
      console.log("body:", formData);

      fetch('http://localhost:7070/questions', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      })
        .then(response => {
          //uncomment the below code when backend is ready
          // if (!response.ok) {
          //   if(response.status == '409'){
          //     alert("Medicine already exists, you can update details instead of re-adding again")
          //     handleModalDialogue(false)
          //   }else{
          //     alert("Error adding medicine, please try again");
          //   }              
          //     throw new Error(response.status);                      
          // } 
          return response.json();
        })
        .then((responseJson) => {
          // uncomment the below when backend is ready or intergrated with it
          // console.log("responseJson.status",responseJson.status)
          // if(responseJson.status === '409') {
          //   alert(responseJson.message)
          // }else{
          //   alert("Question added successfully");
          //   handleModalDialogue(false)
          //   navigate("/admin-dashboard")
          // }     

          // delete the below after integration
          alert("Question added successfully");
          handleModalDialogue(false)
          navigate("/admin-dashboard")
        })
        .catch((error) => {
          console.log('Error occurred while adding question details: ' + error);
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
          <Modal.Title>ADD QUESTION</Modal.Title>
        </Modal.Header>
        <Form onSubmit={formik.handleSubmit}>
          <Modal.Body>
            {[
              { label: 'Question Description*', id: 'questionDescription', type: 'text', placeholder: 'Enter Question' },
              { label: 'Option One*', id: 'optionOne', type: 'text', placeholder: 'Enter option one details' },
              { label: 'Option Two*', id: 'optionTwo', type: 'text', placeholder: 'Enter option two details' },
              { label: 'Option Three', id: 'optionThree', type: 'text', placeholder: 'Enter option three details' },
              { label: 'Option Four', id: 'optionFour', type: 'text', placeholder: 'Enter option four details' },
              { label: 'Correct Answer', id: 'correctAnswer', type: 'text', placeholder: 'Enter Correct Answer' },

            ].map(({ label, id, type, placeholder }) => (
              <Form.Group controlId={id} className="mb-3" key={id}>
                <Form.Label>{label}</Form.Label>
                <Form.Control
                  type={type}
                  placeholder={placeholder}
                  value={formik.values[id] || ''}
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  isInvalid={formik.touched[id] && !!formik.errors[id]}
                  className="custom-input" // Custom class for input fields
                />
                <Form.Control.Feedback type="invalid">
                  {formik.errors[id]}
                </Form.Control.Feedback>
              </Form.Group>
            ))}

            <Form.Group controlId="level" className="mb-3">
              <Form.Label>Level*</Form.Label>
              <Form.Control
                as="select"
                value={formik.values.level}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                isInvalid={formik.touched.level && !!formik.errors.level}
                className="custom-input" // Custom class for select dropdown
              >
                <option value="">Select Level</option>
                <option value="EASY">EASY</option>
                <option value="MEDIUM">MEDIUM</option>
                <option value="HARD">HARD</option>
              </Form.Control>
              <Form.Control.Feedback type="invalid">
                {formik.errors.level}
              </Form.Control.Feedback>
            </Form.Group>
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

export default AddQuestion;
