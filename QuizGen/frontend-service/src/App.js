import React, { Suspense } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import UserQuiz from './components/UserDashboardComponent/UserQuizComponent/UserQuiz';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';
import ContactPage from './pages/ContactPage/ContactPage';
import HomePage from './pages/HomePage/HomePage';
import AdminDashboard from './components/AdminDashboardComponent/AdminDashboard';
import LogoutPage from './pages/LogoutPage/LogoutPage';
import PerformQuiz from './components/PerformQuizComponent/PerformQuiz';


// Lazy load the components
const Login = React.lazy(() => import("./components/LoginComponent/Login"));
const AddQuestion = React.lazy(() => import("./components/AddQuestion/AddQuestion"));
const ResetPassword = React.lazy(() => import("./components/LoginComponent/ResetPassword"));
const User_Register = React.lazy(() => import("./components/RegisterComponent/User_Register"));
const Admin_Register = React.lazy(() => import("./components/RegisterComponent/Admin_Register"));
const ProgressTracking = React.lazy(() => import("./components/UserDashboardComponent/UserProgressTrackingComponent/ProgressTracking"));
const UpdateQuestion = React.lazy(() => import("./components/UpdateQuestionComponent/UpdateQuestion"));



function App() {
    return (
        <Router>
            <Header />
           
            {/* Use Suspense to wrap lazy-loaded components */}
            <Suspense fallback={<div>Loading...</div>}>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/addQuestion" element={<AddQuestion />} />
                    <Route path="/update-question" element={<UpdateQuestion />} />
                    <Route path="/reset-password" element={<ResetPassword />} />
                    <Route path="/user-register" element={<User_Register />} />
                    <Route path="/admin-register" element={<Admin_Register />} />
                    <Route path="/contact" element={<ContactPage />} />
                    <Route path="/logout" element={<LogoutPage />} />
                      <Route path="/userDashboard/progressTracking" element={<ProgressTracking />} />
            <Route path="/userDashboard/quiz" element={<UserQuiz />} />
            <Route path="/admin-dashboard" element={<AdminDashboard />} />
            <Route path="/performQuiz" element={<PerformQuiz />} />
      </Routes>
            </Suspense>
            <Footer />
        </Router>
    );
}

export default App;
