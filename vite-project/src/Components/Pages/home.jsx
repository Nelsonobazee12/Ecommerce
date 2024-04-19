// home.jsx
import React from 'react';
import useCheckTokenExpiration from './useCheckTokenExpiration';

const Home = () => {
    useCheckTokenExpiration();
    return (
        <div>
            <h1>Welcome to the Home Page</h1>
        </div>
    );
};

export default Home;