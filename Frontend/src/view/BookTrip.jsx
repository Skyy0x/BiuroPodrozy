import { useContext, useEffect, useMemo, useState } from "react";
import { set, useForm } from "react-hook-form"
import { useLocation, useNavigate, useParams } from "react-router-dom";
import Navbar from "../component/Navbar";
import { AuthContext } from "../component/auth/AuthProvider";
import PagePadding from "../component/PagePadding";
import Loading from "../component/Loading";
import useTrips from "../hooks/useTrips";
import Input from "../component/Input";
import InputError from "../component/InputError";
import api from "../services/axios";
import useRole from "../hooks/useRole";
import formatDate from "../utils/formatDate";

export default function BookTrip() {
    const userRole = useRole();
    const { token } = useContext(AuthContext);

    const { trips, isLoading, error } = useTrips();

    const navigate = useNavigate()
    const location = useLocation();

    useEffect(() => {
        if (userRole === null) return;
    
        if (!userRole) {
            navigate('/login', {
                replace: true,
                state: { from: location },
            });
        }
    }, [userRole, navigate, location]);    

    const { id } = useParams();

    const trip = useMemo(() => trips.find(t => t.id == id), [trips, id]);

    const {register, handleSubmit, formState: {errors}} = useForm();

    const [peopleNumber, setPeopleNumber] = useState(1);
    const [poepleError, setPeopleError] = useState('');
    const [apiError, setApiError] = useState('');

    const addPeople = (event) => {
        event.preventDefault();

        if(peopleNumber >= trip.maxPeople) {
            setPeopleError(`Liczba osob nie moze byc wieksza niz ${trip.maxPeople}`)
            return
        }

        setPeopleError('');

        setPeopleNumber(peopleNumber + 1);
    }

    const removePeople = (event) => {
        event.preventDefault();

        if(peopleNumber <= 1) {
            setPeopleError(`Liczba osob nie moze byc mniejsza niz 1`)
            return
        }

        setPeopleError('');

        setPeopleNumber(peopleNumber - 1);
    }

    const onReserve = (data) => {
        const peopleData = [];

        for (let i = 0; i < peopleNumber; i++) {
            const person = {
                firstName: data[`firstName_${i}`],
                lastName: data[`lastName_${i}`],
                birthDate: data[`birthDate_${i}`],
                gender: data[`gender_${i}`]?.toUpperCase(),
            };

            peopleData.push(person);
        }

        const payload = {
            customers: peopleData,
        };

        api.post(`/bookings/book/${trip.id}`, payload, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((response) => {
                navigate("/");
            })
            .catch((error) => {
                setApiError("Wystapil nieoczekiwany blad przy rezerwacji");
            });
    };

    const handleManualSubmit = () => {
        handleSubmit(onReserve)();
    };

    if (isLoading) return <Loading message="Ładowanie wycieczek..." />;
    if (error) return <Error message={error} />;
    if (!trip) return <p>Trip not found</p>;

    return (
        <>
            <PagePadding>
                <Navbar/>
                <div className="flex flex-col lg:flex-row">
                    <div className="flex-1 px-4 px-[20px] xl:px-[100px] items-center justify-center">
                        {apiError && (
                            <div className="text-red-500 text-center mb-4">
                                {apiError}
                            </div>
                        )}
                        <form onSubmit={handleSubmit(onReserve)} className="flex justify-center items-center lg:items-start flex-col">
                            {Array.from({ length: peopleNumber }).map((_, index) => (
                                <div key={index} className="flex flex-col w-80 m-6">
                                    <div className="flex justify-between py-4 items-center">
                                        <p className="text-2xl font-bold">
                                            Osoba {index + 1}
                                        </p>

                                        {(index + 1) == peopleNumber && (
                                            <button onClick={(event => removePeople(event))} className="bg-red-500 hover:bg-red-400 text-white font-semibold px-4 py-2 rounded-md">
                                                Usun osobe
                                            </button>
                                        )}
                                    </div>

                                    <div className="flex flex-col py-2">
                                        <label>
                                            Imie
                                        </label>

                                        <Input 
                                            type="text"
                                            name={`firstName_${index}`}
                                            {...register(`firstName_${index}`, { 
                                                required: "Imie jest wymagane" 
                                            })}
                                            errors={errors}
                                        />
                                        <InputError 
                                            errors={errors}
                                            name={`firstName_${index}`}
                                        />
                                    </div>

                                    <div className="flex flex-col py-2">
                                        <label>
                                            Nazwisko
                                        </label>
                                    
                                        <Input 
                                            type="text"
                                            name={`lastName_${index}`}
                                            {...register(`lastName_${index}`, { 
                                                required: "Nazwisko jest wymagane" 
                                            })}
                                            errors={errors}
                                        />
                                        <InputError 
                                            errors={errors}
                                            name={`lastName_${index}`}
                                        />
                                    </div>

                                    <div className="flex flex-col py-2">
                                        <label>
                                            Data urodzenia
                                        </label>

                                        <Input 
                                            type="date"
                                            name={`birthDate_${index}`}
                                            {...register(`birthDate_${index}`, { 
                                                required: "Data urodzenia jest wymagana" 
                                            })}
                                            errors={errors}
                                        />
                                        <InputError 
                                            errors={errors}
                                            name={`birthDate_${index}`}
                                        />

                                    </div>

                                    <div className="flex flex-col py-2">
                                        <label>
                                            Plec
                                        </label>
                                        <select 
                                            {...register(`gender_${index}`, { 
                                                required: "Plec jest wymagana" 
                                            })} 
                                            className={`w-full px-4 py-3 border ${
                                                errors[`gender_${index}`] ? "border-red-500" : "border-gray-300"
                                            } rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400`}
                                        >
                                            <option value="">-- Wybierz plec --</option>
                                            <option value="male">Mezczyzna</option>
                                            <option value="female">Kobieta</option>
                                        </select>

                                        {errors[`gender_${index}`] && (
                                            <p className="text-red-600 text-sm">{errors[`gender_${index}`]?.message}</p>
                                        )}
                                    </div>
                                </div>
                            ))}

                            {poepleError && (
                                <p className="text-red-600 text-md">
                                    {poepleError}
                                </p>
                            )}
                        </form>
                        <div className="flex justify-center">
                            <button onClick={(event) => addPeople(event)} className="bg-blue-500 hover:bg-blue-400 text-white font-semibold px-4 py-2 rounded-md">
                                Dodaj osobe
                            </button>
                        </div>
                    </div>
                    <div className="w-full h-auto lg:h-screen max-h-[calc(100vh-80px)] sticky top-16 z-10 flex flex-col items-center lg:items-end my-2">
                        <div className="h-[250px] flex flex-col justify-between p-8 border border-gray-300 rounded-md gap-4">
                            <p>
                                Do zaplaty: {(peopleNumber * trip.pricePerPerson).toFixed(2)}
                            </p>
                            <div className="text-sm text-gray-500">
                                <p>
                                    Maksymalna liczba osob: <span className="text-black"> {trip.maxPeople} </span>
                                </p>
                                <p>
                                    Opiekun: <span className="text-black"> {trip.tripLeader} </span>
                                </p>
                                <p className="">
                                    {formatDate(trip.startDate)} - {formatDate(trip.endDate)}
                                </p>
                            </div>
                            <div className="flex justify-center">
                                <button onClick={handleManualSubmit} className="bg-blue-500 hover:bg-blue-300 text-white font-semibold px-4 py-2 rounded-md w-full">
                                    Rezerwuj
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </PagePadding>
        </>
    )
}