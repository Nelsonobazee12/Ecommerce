import React, { useState } from 'react';

const RefreshTokenAndRedirect = () => {
    const [message, setMessage] = useState('');

    // Save the current URL as the previous page URL
    // const previousPageUrl = window.location.href;

    // Make a POST request to the /refresh-token endpoint
    fetch('http://localhost:8080/registration/refresh-token', {
        method: 'POST',
        credentials: 'same-origin'
    })
        .then(response => {
            if (response.ok) {
                // Token refreshed successfully
                setMessage('Token refreshed successfully');

                // Redirect the user to the previous page
                // window.location.href = previousPageUrl;
            } else {
                // Handle error if token refresh failed
                setMessage('Failed to refresh token');
            }
        })
        .catch(error => {
            // Handle network error
            setMessage('Network error: ' + error.message);
        });

    return (
        <div>
            <h1>Welcome to the service page</h1>
            <p>{message}</p>
        </div>
    );
}

export default RefreshTokenAndRedirect;

