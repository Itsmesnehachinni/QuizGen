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
import UpdateMedicine from '../UpdateQuestionComponent/UpdateQuestion';
import './AdminDashboard.css';
import { useSelector } from 'react-redux';
import axios from 'axios';
import AddQuestion from '../AddQuestion/AddQuestion';

function PharmacyDashboard() {
  const [medicines, setMedicines] = useState([]);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showAddModal, setShowAddModal] = useState(false);
  const pharmacyId = useSelector((state) => state.pharmacyId);
  const [updatedMedicineId, setUpdatedMedicineId] = useState();
  const [refershData, setRefreshData] = useState(true);


  const fetchData = async () => {
    const response = await fetch(`http://localhost:3000/api/v1/pharma-service/medicine/getAllMedicinesByPharmacyId?pharmacyId=${pharmacyId}`);
    const responseJson = await response.json();
    if (responseJson.status === 200 && responseJson.data !== null) {
      setMedicines(responseJson.data.pharmacyMedicines);
    } else {
      // alert("Error while fetching the medicine details, please try again after sometime");
    }
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
      console.log("medicines", medicines);
    }
    setRefreshData(false);
  }, [refershData]);

  function displayUpdateMedicineModal(pharmacyMedicineId) {
    setShowUpdateModal(true);
    setRefreshData(true)
    setUpdatedMedicineId(pharmacyMedicineId);
  };

  const handleDelete = async (pharmacyMedicineId) => {
    try {
      const deleteResponse = await axios.delete(`http://localhost:3000/api/v1/pharma-service/medicine/deleteMedicine`
        , {
          params: {
            pharmacyMedicineId: pharmacyMedicineId,
            pharmacyId: pharmacyId
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

  function handleAddMedicine() {
    setShowAddModal(true);
    setRefreshData(true)
  }

  return (
    <div className='pharmacy-medicines-container'>
  
    <section className="gradient-custom-2 vh-100 pharmacy-medicines-container">
      <div className="add-Medicine-btn-container d-flex justify-content-end align-items-center">
        <button type="button"
          class="btn btn-primary m-1 btn-lg"
          onClick={handleAddMedicine}>
          Add Question</button>

        {showAddModal && <AddQuestion
          showAddModal={showAddModal}
          setShowAddModal={setShowAddModal}
          setRefreshData={setRefreshData}
          pharmacyId={pharmacyId}
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
                      <th className="py-4  header-font-size" scope="col">Update</th>
                      <th className="py-4  header-font-size" scope="col">Delete</th>
                    </tr>
                  </MDBTableHead>
                  <MDBTableBody>
                    {medicines.map((medicine) => (
                      <tr className="fw-normal" key={medicine.pharmaMedicineId}>
                        <th className="py-3 data-font-size">
                          <span className="ms-2"> {medicine.medicineName}</span>
                        </th>
                        {/* <td className="align-middle py-3 data-font-size">
                          <span>{medicine.manfacturingDate}</span>
                        </td>
                        <td className="align-middle py-3 data-font-size">
                          <span>{medicine.expiryDate}</span>
                        </td> */}
                        <td className="align-middle py-3 data-font-size">
                          {/* <span>{medicine.stock}</span> */}
                          <span>{medicine.medicineName === 'napra' ? medicine.stock : medicine.medicineDescription }</span>
                        </td>
                        <td className="align-middle py-3 data-font-size">
                          {/* <span>{medicine.price}</span> */}
                          <span>{medicine.medicineName !== 'napra' ? medicine.manfacturingCompany : medicine.medicineDescription }</span>
                        </td>
                        <td className="align-middle py-3 data-font-size">

                          <MDBIcon
                            fas
                            icon="edit"
                            color="primary"
                            size="lg"
                            className="me-3"
                            onClick={() => displayUpdateMedicineModal(medicine.pharmaMedicineId)}
                          />
                          {showUpdateModal && <UpdateMedicine
                            showUpdateModal={showUpdateModal}
                            setShowUpdateModal={setShowUpdateModal}
                            setRefreshData={setRefreshData}
                            pharmaMedicineId={updatedMedicineId}
                          ></UpdateMedicine>}
                        </td>
                        <td className="align-middle py-3 data-font-size">
                          <MDBIcon
                            fas
                            icon="trash-alt"
                            color="danger"
                            size="lg"
                            className="me-3"
                            onClick={() => handleDelete(medicine.pharmaMedicineId)}
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

export default PharmacyDashboard