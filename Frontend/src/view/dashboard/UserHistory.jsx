import { useEffect, useState } from "react";
import api from "../../services/axios";
import Booking from "./Booking";
import Loading from "../../component/Loading";
import Error from "../../component/Error";

export default function UserHistory() {
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        api.get("/user/history")
            .then((res) => {
                setBookings(res.data.data.bookings);
            })
            .catch((err) => {
                console.error("Błąd podczas pobierania rezerwacji:", err);
                setError("Nie udało się pobrać rezerwacji.");
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    if (loading) return <Loading message="Ładowanie historii rezerwacji..." />;
    if (error) return <Error message={error} />;

    return (
        <div className="flex justify-start items-center">
            <div className="w-full py-8 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-2 xl:grid-cols-3 gap-4">
                {bookings.length === 0 ? (
                    <p>Brak historii</p>
                ) : (
                    bookings.map((booking) => (
                        <Booking key={booking.id} booking={booking} />
                    ))
                )}
            </div>
        </div>
    );
}
