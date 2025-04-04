import React from 'react';
import { MDBContainer, MDBCol, MDBRow, MDBBtn, MDBInput } from 'mdb-react-ui-kit';
import { useFormik } from 'formik';
import * as yup from 'yup';
import { useNavigate } from "react-router-dom";
import '../../css/style.css';


const validationSchema = yup.object({
  userName: yup.string().required('Invalid User name'),
  oldPassword: yup.string().required('Old Password cannot be left blank'),
  newPassword: yup.string()
  .required('New Password cannot be left blank'),
  confirmpassword: yup.string()
  .required('Confirm Password cannot be left blank')
  .test('confirmpassword', 'New Password & confirm password should be same', function(cpass){
      if(this.parent.newPassword==cpass){
          return true;
      }
      return false;
  })});

function App() {
  const navigate = useNavigate();

  const formik = useFormik({
    initialValues: {
      userName:'',
      oldPassword: '',
      newPassword: '',
      confirmpassword:''
    },
    validationSchema: validationSchema,
    onSubmit: async (values) => {
      try {
        console.log(values);
        const response = await fetch('http://localhost:3000/api/v1/user-service/user/resetPassword', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(values), 
        });
        const responseJson = await response.json();
        console.log("data :",responseJson);
        
        if (responseJson.status === 200) {
          alert("Reset password done successfully!!!");
          navigate("/login");
          return;
        }

        if (responseJson.status === 500) {
          alert("Incorrect credentials to process reset!!!");
          navigate("/reset-password");
          return;
        }
      } catch (error) {
        console.error("Error during login:", error);
        alert("An error occurred. Please try again later.");
      }
    },
  });


  return (
    <MDBContainer fluid className="p-3 my-5 h-custom">
      <MDBRow>
        <MDBCol md='6'>
          <img src="./images/resetpwd.jpg" className="resetpwd-image" alt="Sample image" />
        </MDBCol>

        <MDBCol md='6'>
          <form onSubmit={formik.handleSubmit}>
          <MDBInput
              wrapperClass='mt-3'
              placeholder='Registered User Name'
              id='userName'
              type='userName'
              value={formik.values.userName}
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              isInvalid={formik.touched.userName && !!formik.errors.userName}
            />
            {formik.errors.userName && formik.touched.userName ? <span className="text-danger custom-text-danger">{formik.errors.userName}</span> : null}

            <MDBInput
              wrapperClass='mt-3'
              placeholder='Old Password'
              id='oldPassword'
              type='password'
              value={formik.values.oldPassword}
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              isInvalid={formik.touched.oldPassword && !!formik.errors.oldPassword}
            />
            {formik.errors.oldPassword && formik.touched.oldPassword ? <span className="text-danger custom-text-danger">{formik.errors.oldPassword}</span> : null}

            <MDBInput
              wrapperClass='mt-3'
              placeholder='New Password'
              id='newPassword'
              type='password'
              value={formik.values.newPassword}
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              isInvalid={formik.touched.newPassword && !!formik.errors.newPassword}
            />
            {formik.errors.newPassword && formik.touched.newPassword ? <span className="text-danger custom-text-danger">{formik.errors.newPassword}</span> : null}

            <MDBInput
              wrapperClass='mt-3'
              placeholder='Confirm Password'
              id='confirmpassword'
              type='password'
              value={formik.values.confirmpassword}
              onBlur={formik.handleBlur}
              onChange={formik.handleChange}
              isInvalid={formik.touched.confirmpassword && !!formik.errors.confirmpassword}
            />
            {formik.errors.confirmpassword && formik.touched.confirmpassword ? <span className="text-danger custom-text-danger">{formik.errors.confirmpassword}</span> : null}          
            
            <div className="mt-3 signup-button d-flex justify-content-end">
              <button type="submit" className="btn btn-primary me-2">Submit</button>
              <button type="button" className="btn btn-primary">Cancel</button>
            </div>
          </form>
        </MDBCol>
      </MDBRow>
    </MDBContainer>
);
}

export default App;
