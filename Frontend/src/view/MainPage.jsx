import Trip from "../component/Trip";
import Navbar from "../component/Navbar";
import { useForm } from "react-hook-form";
import { useEffect, useState } from "react";
import axios from "axios";
import PagePadding from "../component/PagePadding";
import Loading from "../component/Loading";
import Input from "../component/Input";
import useTrips from "../hooks/useTrips";

export default function MainPage() {
    const {register, handleSubmit, formState: { errors }} = useForm();

    const { trips, isLoading, error } = useTrips();

    const [allTrips, setAllTrips] = useState([]);
    const [visibleTrips, setVisibleTrips] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [apiError, setApiError] = useState('');

    useEffect(() => {
        setAllTrips(trips);
        setVisibleTrips(trips);
    }, [trips]);
    
    const handleChange = (event) => {
        const query = event.target.value;
        setSearchTerm(query);
        filterResults(query);
    };

    const filterResults = (query) => {
        const filteredResults = allTrips.filter((item) =>
            item.name.toLowerCase().includes(query.toLowerCase())
        );
        setVisibleTrips(filteredResults);
    };

    const onSubmit = (data) => {
        const {
            minPrice,
            maxPrice,
            minDays,
            maxDays,
            startDate,
            endDate,
            peopleNumber
        } = data;

        console.log(minDays)
        console.log(maxDays);

        const requestPayload = {
            dateRangeRequest: startDate && endDate ? {
                startDate,
                endDate
            } : null,
            priceRangeRequest: ((minPrice || maxPrice) && minPrice <= maxPrice) ? {
                minPrice: parseInt(minPrice || "0", 10),
                maxPrice: parseInt(maxPrice || "0", 10)
            } : null,
            durationRangeRequest: ((minDays || maxDays) && minDays <= maxDays) ? {
                minDays: parseInt(minDays || "0", 10),
                maxDays: parseInt(maxDays || "0", 10)
            } : null,
            peopleNumber: parseInt(peopleNumber || "0", 10)
        };
        

        console.log(requestPayload)

        axios.post('http://127.0.0.1:8080/api/trips/search', requestPayload)
            .then(response => {
                setVisibleTrips(response.data.data.trips);
            })
            .catch(error => {
                setApiError("Nieoczekiwany blad po stronie serwera.")
            });
    }

    if (isLoading) return <Loading message="Ładowanie wycieczek..." />;
    if (error) return <Error message={error} />;

    return (
        <>   
            <PagePadding>
                <Navbar/>
                <div className="mt-2 mb-5 flex items-center justify-between sm:flex-row flex-col">
                    <h1 className="text-2xl font-bold mb-4 items-center">Nasze wycieczki</h1>
                    <input
                        type="text"
                        placeholder="Szukaj..."
                        value={searchTerm}
                        onChange={handleChange}
                        className="px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:border-green-700"
                    />
                </div>

                <div className="flex flex-col lg:flex-row gap-6">
                    <form
                        onSubmit={handleSubmit(onSubmit)}
                        className="w-full lg:w-1/4 bg-white p-4 rounded shadow-md"
                    >
                        <div className="mb-4">
                            <p className="text-gray-700 font-bold text-lg pb-1">Cena</p>
                            <div className="flex items-center space-x-2">
                                <div className="flex items-center border border-gray-300 rounded px-2 py-1 focus-within:border-blue-500 focus-within:ring-1 focus-within:ring-purple-500">
                                    <Input 
                                        type="number"
                                        name="minPrice"
                                        min="0"
                                        className="text-left outline-none bg-transparent w-full"
                                        placeholder="od"
                                        {...register("minPrice", {required: false})}
                                        errors={errors}
                                    />

                                    <span className="ml-1 text-gray-700 select-none pointer-events-none">zł</span>
                                </div>
                                <span className="text-black font-bold">-</span>
                                <div className="flex items-center border border-gray-300 rounded px-2 py-1 focus-within:border-blue-500 focus-within:ring-1 focus-within:ring-purple-500">
                                    <Input 
                                        type="number"
                                        name="maxPrice"
                                        min="0"
                                        className="text-left outline-none bg-transparent w-full"
                                        placeholder="do"
                                        {...register("maxPrice", {required: false})}
                                        errors={errors}
                                    />
                                    <span className="ml-1 text-gray-700 select-none pointer-events-none">zł</span>
                                </div>
                            </div>
                        </div>

                        <div className="mb-4">
                            <p className="text-gray-700 font-bold text-lg pb-1">Długość pobytu</p>
                            <div className="flex items-center space-x-2">
                                <select {...register("minDays", {required: false})} className="w-full px-3 py-2 border border-gray-300 rounded-md text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500">
                                    <option value="">Dni minimum</option>
                                    {Array.from({ length: 14 }).map((_, index) => (
                                        <option key={`start-${index}`} value={index}>{index}</option>
                                    ))}
                                </select>
                                <span className="text-black font-bold">-</span>
                                <select {...register("maxDays", {required: false})} className="w-full px-3 py-2 border border-gray-300 rounded-md text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500">
                                    <option value="">Dni maximum</option>
                                    {Array.from({ length: 14 }).map((_, index) => (
                                        <option key={`end-${index}`} value={index}>{index}</option>
                                    ))}
                                </select>
                            </div>
                        </div>

                        <div>
                            <p className="text-gray-700 font-bold text-lg pb-1">Data wycieczki</p>
                            <div className="flex items-center space-x-2 mb-4">
                                <Input 
                                    type="date"
                                    name="startDate"
                                    className="w-full px-2 py-1 border border-gray-300 rounded-md text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    {...register("startDate", {required: false})}
                                    errors={errors}
                                />
                                <span className="text-black font-bold">-</span>
                                <Input 
                                    type="date"
                                    name="endDate"
                                    className="w-full px-2 py-1 border border-gray-300 rounded-md text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    {...register("endDate", {required: false})}
                                    errors={errors}
                                />
                            </div>
                        </div>

                        <div className="mb-4">
                            <p className="text-gray-700 font-bold text-lg pb-1">Liczba osób</p>
                            <select {...register("peopleNumber", {required: false})} className="w-full px-3 py-2 border border-gray-300 rounded-md text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500">
                                {Array.from({ length: 14 }).map((_, index) => (
                                <option key={`people-${index}`} value={index}>{index}</option>
                                ))}
                            </select>
                        </div>

                        <button
                            type="submit"
                            className="w-full bg-blue-500 rounded-md py-2 text-white hover:bg-blue-400"
                            >
                            Szukaj
                        </button>
                    </form>

                    <div className="w-full lg:w-2/3 h-full grid grid-cols-1 md:grid-cols-2 lg:grid-cols-2 xl:grid-cols-3 gap-4">
                        {visibleTrips.length === 0 ? (
                            apiError ? (
                                <p>{apiError}</p>
                            ) : (
                                <p>Brak wycieczek</p>
                            )
                            ) : (
                            visibleTrips.map((trip, index) => <Trip key={index} trip={trip} />)
                        )}
                    </div>
                </div>
            </PagePadding>
        </>
    )
}