import { useEffect, useState } from 'react';
import api from '../services/axios';

export default function useRole() {
    const [userRole, setUserRole] = useState(null);

    useEffect(() => {
        const token = sessionStorage.getItem('token');
        
        if (!token) {
            setUserRole(false);
            return;
        }

        api.get('/user/role')
            .then(response => {
                setUserRole(response.data.data.role);
            })
            .catch(() => {
                sessionStorage.removeItem('token');
                setUserRole(false);
            });
    }, []);

    return userRole;
}
