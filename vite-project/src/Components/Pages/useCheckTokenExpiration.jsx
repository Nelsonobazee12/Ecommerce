import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import * as jwt_decode from "jwt-decode";

const useCheckTokenExpiration = () => {
    const navigate = useNavigate();

   const isTokenExpired = () => {
        const token = Cookies.get('accessToken');

        if (!token) {
            return true;
        }

        try {
            const decodedToken = jwt_decode.jwtDecode(token);
            const currentTime = Date.now() / 1000; // Convert milliseconds to seconds
            return currentTime >= decodedToken.exp;
        } catch (error) {
            console.error('Failed to decode JWT:', error);
            return true;
        }
    };

    useEffect(() => {
        if (isTokenExpired()) {
            navigate('/refresh-token');
        }
    }, [navigate]);
};

export default useCheckTokenExpiration;