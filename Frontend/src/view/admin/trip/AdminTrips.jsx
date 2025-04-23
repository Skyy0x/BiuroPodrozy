import { useEffect, useState } from "react";
import AdminTrip from "./AdminTrip";
import useAllTrips from "../../../hooks/useAllTrips";

export default function AdminTrips() {
    const {trips} = useAllTrips();
    const [searchTerm, setSearchTerm] = useState('');
    const [visibleTrips, setVisibleTrips] = useState([]);

    useEffect(() => {
        setVisibleTrips(trips);
    }, [trips])

    const handleChange = (event) => {
        const query = event.target.value;
        setSearchTerm(query);
        filterResults(query);
    };

    const filterResults = (query) => {
        const filteredResults = trips.filter((item) =>
            item.name.toLowerCase().includes(query.toLowerCase())
        );
        setVisibleTrips(filteredResults);
    };

    return (
        <>
            <div className="w-full">
                <div className='mt-2 mb-5 flex items-center justify-between'>
                    <h1 className="text-2xl font-bold mb-4">Wyciecki</h1>
                    <input
                        type="text"
                        placeholder="Szukaj..."
                        value={searchTerm}
                        onChange={handleChange}
                        className="px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:border-green-700"
                    />
                </div>
                <div className="w-full lg:w-3/3 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
                        {visibleTrips.length == 0
                        ? <span>Brak wycieczek</span> 
                        : visibleTrips.map((trip, index) => (
                            <div key={index} className="flex justify-start items-center w-full m-[10px]">
                                <AdminTrip trip={trip}/>
                            </div>
                        ))}
                </div>
            </div>
        </>
    )
}