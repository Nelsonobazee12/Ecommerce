import React, { useState } from "react";
import styles from "./register.module.css";
import { Link } from "react-router-dom";
import Error from '../Pages/Error';
import Cookies from 'js-cookie';

const RegistrationForm = () => {
  const [registrationFormData,
    setRegistrationFormData] = useState({
    firstname: "",
    lastname: "",
    email: "",
    password: "",
  });

  const [error, setError] = useState(null);

  const handleRegistrationChange = (e) => {
    const { name, value } = e.target;
    setRegistrationFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleRegistrationSubmit = (e) => {
    e.preventDefault();

    fetch("http://localhost:8080/registration/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(registrationFormData),
    })
        .then(response => {
          if (!response.ok) {
            throw 'Registration failed. Please check your input or try again later.';
          }
          return response.json();
        })

        .then((data) => {
          const { access_token, refresh_token } = data;
          if (access_token && refresh_token) {
            setCookies(access_token, refresh_token);
            console.log('Success:', data);

          } else {
            throw new Error('Authentication token or refresh token is missing or invalid.');
          }
        })

      .catch((error) => {
        console.error("Error:", error);
        setError('Registration failed.');
        setTimeout(() => {
          setError(null);
        }, 2000)
      });

    console.log("Registering with:", registrationFormData);
  };

  const setCookies = (authToken, refreshToken) => {
    // Set access token and refresh token in cookies with expiration times
    Cookies.set('accessToken', authToken, { expires: 1, secure: true, sameSite: 'strict' });
    Cookies.set('refreshToken', refreshToken, { expires: 7, secure: true, sameSite: 'strict' });
  };



  return (
    <div className={styles.container}>
      <form onSubmit={handleRegistrationSubmit} method="Post" className={styles.form}>
        <div className={styles.main}>
          {error && <Error errorMessage={error} />}
          <h2>Create Account</h2>
          
          <div className={styles.dis__flex}>
            <div>
              <input
                type="text"
                name="firstname"
                value={registrationFormData.firstname}
                onChange={handleRegistrationChange}
                placeholder="firstname"
                required
              />
            </div>
            <div>
              <input
                type="text"
                name="lastname"
                value={registrationFormData.lastname}
                onChange={handleRegistrationChange}
                placeholder="lastname"
                required
              />
            </div>
            <div>
              <input
                type="email"
                name="email"
                value={registrationFormData.email}
                onChange={handleRegistrationChange}
                placeholder="email"
                required
              />
            </div>
            <div>
              <input
                type="password"
                name="password"
                value={registrationFormData.password}
                onChange={handleRegistrationChange}
                placeholder="password"
                required
              />
            </div>
            <button type="submit">Create Account</button><br/>
            <Link to="/login">login</Link>
          </div>
        </div>
      </form>
      {/* <div className={styles.right__img}>
        <img
          src="/images/pexels-franco-monsalvo-252430633-13332850.jpg"
          alt="Description of the image"
        />
      </div> */}
    </div>
  );
};

export default RegistrationForm;
