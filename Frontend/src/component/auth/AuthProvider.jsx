import { createContext, useEffect, useState, useRef } from "react";
import { jwtDecode } from "jwt-decode";
import axios from "axios";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState('');
    const [loggedState, setLoggedState] = useState(false);
    const timeoutRef = useRef(null);

    useEffect(() => {
        const token = sessionStorage.getItem("token");

        if (!token) return;

        setToken(token);

        const decoded = jwtDecode(token);
        const expTime = decoded.exp * 1000;
        const currentTime = Date.now();

        if (expTime < currentTime) {
            logout();
            return;
        }

        setLoggedState(true);

        const timeout = expTime - currentTime;
        timeoutRef.current = setTimeout(logout, timeout);

        return () => {
            clearTimeout(timeoutRef.current);
        };
    }, []);

    const logout = () => {
        sessionStorage.removeItem("token");
        setToken(null);
    };

    return (
        <AuthContext.Provider value={{token, loggedState, logout }}>
            {children}
        </AuthContext.Provider>
    );
};
