import { useNavigate, useParams } from "react-router-dom";
import { useMemo } from "react";
import Navbar from "../component/Navbar";
import Trip from "../component/Trip";
import PagePadding from "../component/PagePadding";
import useTrips from "../hooks/useTrips";
import Loading from "../component/Loading";
import Error from "../component/Error";
import formatDate from "../utils/formatDate";

export default function TripPage() {
    const navigate = useNavigate();
    const { id } = useParams();
    const { trips, isLoading, error } = useTrips();

    const trip = useMemo(() => trips.find(t => t.id == id), [trips, id]);
    const otherTrips = useMemo(() => trips.filter(t => t.id != id), [trips, id]);

    if (isLoading) return <Loading message="Ładowanie wycieczek..." />;
    if (error) return <Error message={error} />;
    if (!trip) return <Error message="Error not found" />;

    return (
        <PagePadding>
            <Navbar />
            <div className="flex flex-col mb-12">
                <h2 className="text-xl my-4 font-semibold text-gray-900">{trip.name}</h2>

                <div className="flex flex-col lg:flex-row justify-between gap-6">
                    <div className="flex flex-col gap-6 lg:flex-row lg:w-3/4">
                        <div className="w-full lg:w-1/2">
                            <img
                                className="object-cover w-full h-full rounded-md max-h-[300px]"
                                src="../gerber.jpg"
                                alt="trip image"
                            />
                        </div>

                        <div className="flex flex-col justify-between w-full lg:w-1/2">
                            <p className="text-gray-600">{trip.description}</p>
                            <div className="text-sm text-gray-500 mt-4 space-y-1">
                                <p>
                                    Maksymalna liczba osób: <span className="text-black">{trip.maxPeople}</span>
                                </p>
                                <p>
                                    Opiekun: <span className="text-black">{trip.tripLeader}</span>
                                </p>
                                <p>
                                    {formatDate(trip.startDate)} - {formatDate(trip.endDate)}
                                </p>
                            </div>
                        </div>
                    </div>

                    <div className="flex flex-col items-center w-full lg:w-[300px] gap-4">
                        <div>
                            <span className="text-xl font-bold text-red-600">
                                {trip.pricePerPerson} PLN
                            </span>
                            <span className="text-gray-400">/osoba</span>
                        </div>
                        <button
                            onClick={() => navigate(`/book/${trip.id}`)}
                            className="bg-blue-500 hover:bg-blue-300 text-white font-semibold px-4 py-2 rounded-md w-full"
                        >
                            Rezerwuj
                        </button>
                    </div>
                </div>
            </div>

            <p className="text-black text-2xl font-bold mb-4">Inne wycieczki</p>

            <div className="w-full px-2 lg:w-2/3 mx-auto grid grid-cols-1 sm:grid-cols-2 xl:grid-cols-3 gap-4 place-items-center">
                {otherTrips.length === 0 ? (
                    <p>Brak wycieczek</p>
                ) : (
                    otherTrips.map((trip, index) => (
                        <Trip key={index} trip={trip} />
                    ))
                )}
            </div>
        </PagePadding>
    );
}
