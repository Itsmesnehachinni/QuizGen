import React from 'react'
import {
  MDBBadge,
  MDBCard,
  MDBCardBody,
  MDBCol,
  MDBContainer,
  MDBIcon,
  MDBRow,
  MDBTable,
  MDBTableBody,
  MDBTableHead,
} from "mdb-react-ui-kit";
import { useEffect, useState } from 'react';
import UpdateQuestion from '../UpdateQuestionComponent/UpdateQuestion';
import './AdminDashboard.css';
import { useSelector } from 'react-redux';
import axios from 'axios';
import AddQuestion from '../AddQuestion/AddQuestion';

function AdminDashboard() {
  const [questions, setQuestions] = useState([]);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showAddModal, setShowAddModal] = useState(false);
  const [updatedQuestionId, setUpdatedQuestionId] = useState();
  const [refershData, setRefreshData] = useState(true);


  const fetchData = async () => {
    const response = await fetch(`http://localhost:7070/questions`);
    const responseJson = await response.json();    
    //uncomment the below when integrated with original backend
    // if (responseJson.status === 200 && responseJson.data !== null) {
    //   console.log("setting questions")
      
    //   setQuestions(responseJson.data.questions);
    // } else {
    //   // alert("Error while fetching the medicine details, please try again after sometime");
    // }
    
    //uncomment the below line after back end integrations
    setQuestions(responseJson);
  }

  // useEffect(() => {
  //   if (!showModal) {
  //     fetchData();
  //     console.log("medicines", medicines);
  //   }
  // }, [showModal]);

  useEffect(() => {
    if (refershData) {
      fetchData();
      console.log("questions:", questions);
    }
    setRefreshData(false);
  }, [refershData]);

  function displayUpdateQuestionModal(questionId) {
    console.log("Admin Dashboard: updatedQuestionId",questionId)
    setShowUpdateModal(true);
    setRefreshData(true)
    setUpdatedQuestionId(questionId);
  };

  const handleDelete = async (QuestionId) => {
    try {
      const deleteResponse = await axios.delete(`http://localhost:7070/questions/${QuestionId}`
        , {
          params: {
            QuestionId: QuestionId
          }
        });
      if (deleteResponse.status === 200) {
        setRefreshData(true);
        alert("Question deleted successfully");
      }
      else {
        alert("Error while deleting the medicine, please try again");
      }

    } catch (err) {
      console.error("Error deleting item:", err);
    }
  };

  function handleAddQuestion() {
    setShowAddModal(true);
    setRefreshData(true)
  }

  return (
    <div className='pharmacy-medicines-container'>
  
    <section className="gradient-custom-2 vh-100 pharmacy-medicines-container">
      <div className="add-Medicine-btn-container d-flex justify-content-end align-items-center">
        <button type="button"
          class="btn btn-primary m-1 btn-lg"
          onClick={handleAddQuestion}>
          Add Question</button>

        {showAddModal && <AddQuestion
          showAddModal={showAddModal}
          setShowAddModal={setShowAddModal}
          setRefreshData={setRefreshData}
        ></AddQuestion>}


      </div>
      <MDBContainer className="py-5 h-100">
        <MDBRow className="d-flex justify-content-center align-items-center">
          <MDBCol md="12" xl="10">
            <MDBCard className="mask-custom"
              style={{ borderRadius: ".75rem", backgroundColor: "#eff1f2" }}>
              <MDBCardBody className="p-4 text-white">

                <MDBTable className="text-white mb-0">
                  <MDBTableHead>
                    <tr>
                      <th className="py-4 header-font-size" scope="col">Question</th>
                      {/* <th className="py-4  header-font-size" scope="col">Manufacturing Date</th>
                      <th className="py-4 header-font-size" scope="col">Expiry Date</th> */}
                      <th className="py-4  header-font-size" scope="col">Option 1</th>
                      <th className="py-4  header-font-size" scope="col">Option 2</th>
                      <th className="py-4  header-font-size" scope="col">Option 3</th>
                      <th className="py-4  header-font-size" scope="col">Option 4</th>
                      <th className="py-4  header-font-size" scope="col">Answer</th>
                      <th className="py-4  header-font-size" scope="col">Level</th>
                      <th className="py-4  header-font-size" scope="col">Update</th>
                      <th className="py-4  header-font-size" scope="col">Delete</th>
                    </tr>
                  </MDBTableHead>
                  <MDBTableBody>
                    {questions.map((question) => (
                      <tr className="fw-normal" key={question.id}>
                        <th className="py-3 data-font-size">
                          <span className="ms-2"> {question.questionDescription}</span>
                        </th>
                        <td className="align-middle py-3 data-font-size">
                          <span>{question.optionOne}</span>
                        </td>
                        <td className="align-middle py-3 data-font-size">
                          <span>{question.optionTwo}</span>
                        </td>
                        <td className="align-middle py-3 data-font-size">
                          <span>{question.optionThree}</span>
                        </td>
                        <td className="align-middle py-3 data-font-size">
                          <span>{question.optionFour}</span>
                        </td>
                        <td className="align-middle py-3 data-font-size">
                          <span>{question.correctAnswer}</span>
                        </td>
                        <td className="align-middle py-3 data-font-size">
                          <span>{question.level}</span>
                        </td>

                        {/* static page data */}
                        
                       
                        <td className="align-middle py-3 data-font-size">

                          <MDBIcon
                            fas
                            icon="edit"
                            color="primary"
                            size="lg"
                            className="me-3"
                            onClick={() => displayUpdateQuestionModal(question.id)}
                          />
                          {showUpdateModal && <UpdateQuestion
                            showUpdateModal={showUpdateModal}
                            setShowUpdateModal={setShowUpdateModal}
                            setRefreshData={setRefreshData}
                            updatedQuestionId={updatedQuestionId}
                          ></UpdateQuestion>}
                        </td>
                        <td className="align-middle py-3 data-font-size">
                          <MDBIcon
                            fas
                            icon="trash-alt"
                            color="danger"
                            size="lg"
                            className="me-3"
                            onClick={() => handleDelete(question.id)}
                          />
                        </td>
                      </tr>


                    ))};

                  </MDBTableBody>
                </MDBTable>
              </MDBCardBody>
            </MDBCard>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
    </section>
    </div>
  );
}

export default AdminDashboard;