import { useEffect, useState } from 'react';
import api from '../services/axios';

const useTrips = () => {
    const [trips, setTrips] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        let isMounted = true;

        api.get('/trips')
            .then((response) => {
                if (isMounted) {
                    setTrips(response.data.data.trips);
                    setIsLoading(false);
                }
            })
            .catch((err) => {
                if (isMounted) {
                    console.error('Error fetching trips:', err);
                    setError('Nie udało się pobrać wycieczek.');
                    setIsLoading(false);
                }
            });

        return () => {
            isMounted = false;
        };
    }, []);

    return { trips, isLoading, error };
};

export default useTrips;
