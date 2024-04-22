
import React, { useState, useEffect } from 'react';


export const Error = ({ errorMessage }) => {
    const [visible, setVisible] = useState(true);

    useEffect(() => {
        const timeout = setTimeout(() => {
            setVisible(false);
        }, 2000); // Hide the error message after 5 seconds

        return () => clearTimeout(timeout);
    }, []);

    return (
        <div className={`error-card ${visible ? 'visible' : 'hidden'}`}>
            <p>{errorMessage}</p>
        </div>
    );
};



export const Success = ({ successMessage }) => {
    const [visible, setVisible] = useState(true);

    useEffect(() => {
        const timeout = setTimeout(() => {
            setVisible(false);
        }, 2000); // Hide the success message after 2 seconds

        return () => clearTimeout(timeout);
    }, []);

    return (
        <div className={`success-card ${visible ? 'visible' : 'hidden'}`}>
            <p>{successMessage}</p>
        </div>
    );
};


