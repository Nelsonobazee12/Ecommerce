
import React, { useState, useEffect } from 'react';



const Error = ({ errorMessage }) => {
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

export default Error;

