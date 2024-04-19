import React, {useEffect, useState} from 'react';
import { Link, useNavigate } from 'react-router-dom'; 
import styles from "../Registration/register.module.css";
import Error from '../Pages/Error';
import Cookies from 'js-cookie';
import { isTokenExpired } from "../Pages/utils.jsx";



const LoginForm = () => {
    const navigate = useNavigate();
    const [loginFormData, setLoginFormData] = useState({ email: '', password: '' });
    const [error, setError] = useState(null);

    const handleLoginChange = (e) => {
        const { name, value } = e.target;
        setLoginFormData(prevData => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleLoginSubmit = (e) => {
        e.preventDefault();
        fetch('http://localhost:8080/registration/authenticate', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(loginFormData),
        })

            .then(response => {
                if (!response.ok) {
                    throw new Error('Login failed. Please check your input or try again later.');
                }
                return response.json();
            })

            .then(data => {
                const { access_token: accessToken, refresh_token: refreshToken } = data;
                if (accessToken && refreshToken) {
                    setCookies(accessToken, refreshToken);

                    setTimeout(() => {
                        console.log('Navigating to /home');
                        navigate("/home");
                    }, 100);
                } else {
                    throw new Error('Authentication token or refresh token is missing or invalid.');
                }
            })

            .catch(error => {
                setError('Login failed. Please check your input or try again later.');
                setTimeout(() => setError(null), 2000);
            });
    };

    const setCookies = (accessToken, refreshToken) => {
        Cookies.set('accessToken', accessToken, { expires: 1, secure: true, sameSite: 'strict' });
        Cookies.set('refreshToken', refreshToken, { expires: 7, secure: true, sameSite: 'strict' });
        navigate("/home");
    };


    // useEffect(() => {
    //     if (isTokenExpired()) {
    //         Cookies.remove('accessToken');
    //     }
    // }, [])


    return (
        <div className={styles.container}>
              <form onSubmit={handleLoginSubmit} className={styles.form}>
                  {error && <Error errorMessage={error} />}
            <h2>Login</h2>
            <div>
                <input
                    type="email"
                    name="email"
                    value={loginFormData.email}
                    onChange={handleLoginChange}
                    placeholder='email'
                    required
                />
            </div>
            <div>
                <input
                    type="password"
                    name="password"
                    value={loginFormData.password}
                    onChange={handleLoginChange}
                    placeholder='password'
                    required
                />
            </div>
            <button type="submit">Login</button>
            <Link to="/register">Sign up</Link>
        </form>
        </div>
    );
};

export default LoginForm;

