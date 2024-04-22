import React from 'react';
import { Navigate } from 'react-router-dom';
import {isAuthenticated} from "./Utils/authService.jsx";


const PrivateRoute = ({ element }) => {
    return isAuthenticated() ? element : <Navigate to="/login" />;
};

export default PrivateRoute;
