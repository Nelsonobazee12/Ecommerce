import Cookies from "js-cookie";
import * as jwt_decode from "jwt-decode";

export const isTokenExpired = () => {
    const token = Cookies.get('accessToken');

    if (!token) {
        console.log('Token not found');
        return true;
    }

    try {
        const decodedToken = jwt_decode.jwtDecode(token);
        console.log('Decoded Token:', decodedToken);
        const { exp } = decodedToken;
        const currentTime = Date.now() / 1000; // Convert milliseconds to seconds
        return currentTime >= exp;
    } catch (error) {
        console.error('Failed to decode JWT:', error);
        return true;
    }
};