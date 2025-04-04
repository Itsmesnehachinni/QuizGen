import React, { useState } from 'react';
import { MDBContainer, MDBCol, MDBRow, MDBBtn, MDBInput } from 'mdb-react-ui-kit';
import { faFacebookF, faTwitter } from '@fortawesome/free-brands-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useFormik } from 'formik';
import { useNavigate } from "react-router-dom";
import * as yup from 'yup';
import '../../css/style.css';
import { useDispatch, useSelector } from 'react-redux';


const validationSchema = yup.object({
  userName: yup.string().required('Invalid User name'),
  password: yup.string().min(6, 'Password should be at least 6 characters').required('Password should be at least 6 characters'),
});

function App() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const formik = useFormik({
    initialValues: {
      userName: '',
      password: '',
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      try {
        const response = await fetch('http://localhost:3000/api/v1/user-service/user/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(values), // Send the login credentials
        });
        const responseJson = await response.json();
        console.log("data :", responseJson);

        if (responseJson.status === 404) {
          alert("No user found. Kindly register to proceed further");
          navigate("/");
          return;
        }

        if (responseJson.status === 200 && responseJson.data !== null &&
          responseJson.data.userName === values.userName && responseJson.data.password === values.password) {
          dispatch({ type: 'SET_ROLE_LOGIN', payload: responseJson.data.role });
          dispatch({ type: 'SET_IS_LOGGED_IN', payload: 'true' });
          if (responseJson.data.role === 'CUSTOMER') {
            // alert("User logged in successfully!!!");
            dispatch({ type: 'SET_CUSTOMER_ID', payload: responseJson.data.id });
            navigate("/userDashboard");
          } else {
            // alert("User logged in successfully!!!");
            dispatch({ type: 'SET_PHARMACY_ID', payload: responseJson.data.id });
            navigate("/admin-dashboard");
          }
        } else {
          alert("Incorrect credentials. Please try with valid credentials");
        }
      } catch (error) {
        console.error("Error during login:", error);
        alert("An error occurred. Please try again later.");
      }
    },
  });

  // console.log ( "role:" + useSelector((state) => state.role))
  // console.log ( "customerId:" + useSelector((state) => state.customerId))
  // console.log ( "pharmacyId:" + useSelector((state) => state.pharmacyId))
  // console.log ("isloggedIn:" +  useSelector((state) => state.isLoggedIn))


  return (
    <MDBContainer fluid className="p-3 my-5 ">
      <MDBRow>
        <MDBCol col='5' md='6'>
          <img src="./images/login.jpg" className="login-image" alt="Sample image" />
        </MDBCol>



        <MDBCol col='7' md='6'>
          <div className="d-flex flex-row align-items-center justify-content-center">
            <p className="lead fw-normal mb-0 me-3">Sign in with</p>
            {/* <MDBBtn floating size='md' tag='a' className='me-2 mdb-btn' >
              <FontAwesomeIcon icon={faFacebookF} />
            </MDBBtn>
            <MDBBtn floating size='md' tag='a' className='me-2 mdb-btn' >
              <FontAwesomeIcon icon={faTwitter} />
            </MDBBtn> */}
            <button type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1 btn-lg">
              <i class="fab fa-google"></i>
            </button>

            <button type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1 btn-lg">
              <i class="fab fa-twitter"></i>
            </button>

            <button type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1 btn-lg">
              <i class="fab fa-facebook-f"></i>
            </button>
          </div>

          <div className="divider d-flex align-items-center my-4">
            <p className="text-center fw-bold mx-3 mb-0">Or</p>
          </div>
          <form onSubmit={formik.handleSubmit} className="w-75 mx-3">
            <MDBInput
              wrapperClass='mt-3'
              placeholder='User Name'
              id='userName'
              type='userName'
              size="lg"
              value={formik.values.userName}
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              isInvalid={formik.touched.userName && !!formik.errors.userName}
            />
            {formik.errors.userName && formik.touched.userName ? <span className="text-danger custom-text-danger ">{formik.errors.userName}</span> : null}

            <MDBInput
              wrapperClass='mt-3'
              placeholder='Password'
              id='password'
              type='password'
              size="lg"
              value={formik.values.password}
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              isInvalid={formik.touched.password && !!formik.errors.password}
            />
            {formik.errors.password && formik.touched.password ? <span className="text-danger custom-text-danger">{formik.errors.password}</span> : null}

            <div className="d-flex justify-content-between mt-3">
              <a href="/reset-password">Reset password</a>
            </div>

            <div className='text-center text-md-start mt-4 pt-2'>
              <button class="btn btn-primary" data-mdb-ripple="false" type="submit">Login</button>
            </div>

          </form>

        </MDBCol>

      </MDBRow>
    </MDBContainer>
  );
}

export default App;
