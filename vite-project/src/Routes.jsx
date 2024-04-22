// Routes.jsx
import React from 'react';
import { useRoutes } from 'react-router-dom';
import LoginForm from './Pages/Login/login';
import RegistrationForm from './Pages/Registration/register';
import Home from "./Pages/Home/home.jsx";
import PrivateRoute from "./PrivateRoutes.jsx";
import Service from "./Pages/Home/service.jsx";
import RefreshTokenAndRedirect from "./Utils/tokenRefresh.jsx";



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
            path: '/',
            element: <PrivateRoute element={<Home />} />,
        },
        {
            path: '/home',
            element: <PrivateRoute element={<Home />} />,
        },
        {
            path: '/service',
            element: <PrivateRoute element={<Service />} />,
        },
        {
            path: '/refresh-token',
            element: <RefreshTokenAndRedirect />,
        },
    ]);
};

export default Routes;