// Routes.jsx
import React from 'react';
import { useRoutes } from 'react-router-dom';
import LoginForm from './Components/Login/login';
import RegistrationForm from './Components/Registration/register';
import Home from "./Components/Pages/home.jsx";
import PrivateRoute from "./PrivateRoutes.jsx";
import Service from "./Components/Pages/service.jsx";
import RefreshTokenAndRedirect from "./Components/Pages/tokenRefresh.jsx";



const Routes = () => {
    return useRoutes([
        {
            path: '/login',
            element: <LoginForm />,
        },
        {
            path: '/register',
            element: <RegistrationForm />,
        },
        {
            path: '/home',
            element: <PrivateRoute element={<Home />} />, // Render the TestComponent
        },
        {
            path: '/service',
            element: <PrivateRoute element={<Service />} />, // Render the TestComponent
        },
        {
            path: '/refresh-token',
            element: <RefreshTokenAndRedirect />,
        },
    ]);
};

export default Routes;