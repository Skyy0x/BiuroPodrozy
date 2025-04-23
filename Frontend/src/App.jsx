import React, { useContext, useEffect, useMemo } from "react";
import { BrowserRouter, Routes, Route, useNavigate } from "react-router-dom";

import MainPage from "./view/MainPage";
import TripPage from "./view/TripPage";
import BookTrip from "./view/BookTrip";
import Login from "./view/auth/Login";
import Register from "./view/auth/Register";
import UserPanel from "./view/dashboard/UserPanel";
import AdminPanel from "./view/admin/AdminPanel";
import NotFound from "./view/NotFound";

import { AuthContext } from "./component/auth/AuthProvider";
import useRole from "./hooks/useRole";

function Logout() {
    const { logout } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        logout();
        navigate("/");
    }, [logout, navigate]);

    return null;
}

const PUBLIC_ROUTES = [
    { path: "/login", element: <Login /> },
    { path: "/register", element: <Register /> },
];

const USER_ROUTES = [
    { path: "/dashboard", element: <UserPanel /> },
];

const ADMIN_ROUTES = [
    { path: "/dashboard", element: <AdminPanel /> },
];

export default function App() {
    const role = useRole();

    const roleBasedRoutes = useMemo(() => {
        if (role === "ADMIN") return ADMIN_ROUTES;
        if (role === "USER") return USER_ROUTES;
        return PUBLIC_ROUTES;
    }, [role]);

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<MainPage />} />
                <Route path="/trip/:id" element={<TripPage />} />
                <Route path="/book/:id" element={<BookTrip />} />
                <Route path="/logout" element={<Logout />} />

                {roleBasedRoutes.map(({ path, element }, idx) => (
                <Route key={idx} path={path} element={element} />
                ))}

                <Route path="/*" element={<NotFound />} />
            </Routes>
        </BrowserRouter>
    );
}
