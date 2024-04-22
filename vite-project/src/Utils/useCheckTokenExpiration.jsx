import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import * as jwt_decode from "jwt-decode";

const useCheckTokenExpiration = () => {
    const navigate = useNavigate();

   const isTokenExpired = () => {
        const accessToken = Cookies.get('accessToken');
        const refreshToken = Cookies.get('refreshToken');

        if (!accessToken) {
            return true;
        }

        try {
            const decodedToken = jwt_decode.jwtDecode(accessToken);
            const currentTime = Date.now() / 1000;
            return currentTime >= decodedToken.exp;
        } catch (error) {
            console.error('Failed to decode JWT:', error);
            return true;
        }
    };

    useEffect(() => {
        if (isTokenExpired()) {
            navigate('/login');
        }
    }, [navigate]);

    useEffect(() => {
        if (isTokenExpired()) {
            Cookies.remove('accessToken');
            Cookies.remove('refreshToken');
        }
    }, [])
};

export default useCheckTokenExpiration;