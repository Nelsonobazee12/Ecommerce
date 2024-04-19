import React from 'react';
import { BrowserRouter, Navigate } from 'react-router-dom';
import Routes from './Routes';

const App = () => {
    return (
        <BrowserRouter>
            <Routes />
        </BrowserRouter>
    );
};

export default App;

