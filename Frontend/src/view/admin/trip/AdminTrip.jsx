import { useEffect, useRef, useState } from "react";
import { useForm } from "react-hook-form";
import api from "../../../services/axios";
import Modal from "../../../component/Modal";
import InputError from "../../../component/InputError";
import Input from "../../../component/Input";

const formatDateArray = (arr) => {
    if (!Array.isArray(arr) || arr.length !== 3) return "";
    const [year, month, day] = arr;
    return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
};

export default function AdminTrip({ trip }) {
    const {
        register,
        handleSubmit: handleSubmitUpdate,
        reset,
        formState: { errors }
    } = useForm({
        defaultValues: {
            name: trip.name,
            description: trip.description,
            pricePerPerson: trip.pricePerPerson,
            startDate: formatDateArray(trip.startDate),
            endDate: formatDateArray(trip.endDate),
            active: trip.active,
            maxPeople: trip.maxPeople,
            tripLeader: trip.tripLeader
        }
    });

    const { handleSubmit: handleSubmitDelete } = useForm();
    const [deleteError, setDeleteError] = useState('');
    const [updateError, setUpdateError] = useState('');
    const [showModal, setShowModal] = useState(false);
    const [showBookingsModal, setShowBookingsModal] = useState(false);
    const [bookings, setBookings] = useState([]);
    const modalRef = useRef(null);

    const openModal = () => {
        setShowModal(true);
        setTimeout(() => {
            modalRef.current?.scrollIntoView({ behavior: "smooth", block: "center" });
        }, 50);
    };

    const closeModal = () => {
        setShowModal(false);
        reset();
    };

    const openBookingsModal = async () => {
        try {
            const res = await api.get(`/manage/trips/${trip.id}`);
            setBookings(res.data.data.bookings || []);
            setShowBookingsModal(true);

            setTimeout(() => {
                modalRef.current?.scrollIntoView({ behavior: "smooth", block: "center" });
            }, 50);
        } catch (err) {
            console.error("Failed to fetch bookings", err);
        }
    };

    const closeBookingsModal = () => setShowBookingsModal(false);

    const onSubmitDelete = () => {
        api.delete(`/manage/trips/${trip.id}`)
            .then(() => window.location.reload())
            .catch(() => setDeleteError("Coś poszło nie tak podczas usuwania wycieczki."));
    };

    const onSubmitUpdate = (data) => {
        api.put(`/manage/trips/${trip.id}`, data)
            .then(() => {
                closeModal();
                window.location.reload();
            })
            .catch(() => setUpdateError("Błąd podczas aktualizacji wycieczki."));
    };

    return (
        <>
            <div className="flex flex-col gap-3 p-4 border border-gray-200 rounded-xl shadow-sm bg-white w-full max-w-xs">
                <p className="text-lg font-semibold text-gray-800">{trip.name}</p>

                <div className="text-sm text-gray-600 space-y-1 font-medium">
                    <p><span>Maks osób:</span> {trip.maxPeople}</p>
                    <p><span>Od:</span> {trip.startDate.join("-")}</p>
                    <p><span>Do:</span> {trip.endDate.join("-")}</p>
                    <p><span>Cena:</span> {trip.pricePerPerson}</p>
                    <p>
                        <span>Status:</span>{" "}
                        <span className={`inline-block px-2 py-0.5 rounded-full text-xs font-semibold ${trip.active ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"}`}>
                            {trip.active ? "Aktywna" : "Nieaktywna"}
                        </span>
                    </p>
                </div>

                <form onSubmit={handleSubmitDelete(onSubmitDelete)} className="w-full">
                    {deleteError && <p className="text-red-500 text-sm mb-2">{deleteError}</p>}
                    <button
                        type="submit"
                        className="w-full py-2 rounded-md bg-red-600 hover:bg-red-700 text-white font-semibold text-sm transition"
                    >
                        Usuń wycieczkę
                    </button>
                </form>

                <button
                    onClick={openModal}
                    type="button"
                    className="w-full py-2 rounded-md border border-gray-300 text-sm text-gray-600 hover:bg-gray-100 transition"
                >
                    Edytuj
                </button>

                <button
                    onClick={openBookingsModal}
                    type="button"
                    className="w-full py-2 rounded-md bg-blue-600 text-white text-sm font-semibold hover:bg-blue-700 transition"
                >
                    Sprawdź Rezerwacje
                </button>

                <Modal show={showModal} close={closeModal}>
                    <div ref={modalRef} className="p-4">
                        <h2 className="text-xl font-semibold mb-4">Edytuj wycieczkę</h2>

                        <form onSubmit={handleSubmitUpdate(onSubmitUpdate)} className="space-y-4">
                        {updateError && (
                            <div className="text-red-500 text-center mb-4">
                                {updateError}
                            </div>
                        )}
                            <div>
                                <Input
                                    type="text"
                                    name="name"
                                    placeholder="Nazwa"
                                    {...register("name", { required: "Nazwa jest wymagana" })}
                                    errors={errors}
                                />
                                <InputError errors={errors} name="name" />
                            </div>

                            <div>
                                <Input
                                    type="text"
                                    name="description"
                                    placeholder="Opis"
                                    {...register("description", { required: "Opis jest wymagany" })}
                                    errors={errors}
                                />
                                <InputError errors={errors} name="description" />
                            </div>

                            <div>
                                <Input
                                    type="number"
                                    name="pricePerPerson"
                                    placeholder="Cena za osobę"
                                    min="0"
                                    step="0.01"
                                    {...register("pricePerPerson", { required: "Cena jest wymagana" })}
                                    errors={errors}
                                />
                                <InputError errors={errors} name="pricePerPerson" />
                            </div>

                            <div className="flex gap-2">
                                <div className="w-1/2">
                                    <Input
                                        type="date"
                                        name="startDate"
                                        {...register("startDate", { required: "Data rozpoczęcia jest wymagana" })}
                                        errors={errors}
                                    />
                                    <InputError errors={errors} name="startDate" />
                                </div>
                                <div className="w-1/2">
                                    <Input
                                        type="date"
                                        name="endDate"
                                        {...register("endDate", { required: "Data zakończenia jest wymagana" })}
                                        errors={errors}
                                    />
                                    <InputError errors={errors} name="endDate" />
                                </div>
                            </div>

                            <div>
                                <Input
                                    type="number"
                                    name="maxPeople"
                                    placeholder="Maksymalna liczba osób"
                                    min="1"
                                    {...register("maxPeople", { required: "Maksymalna liczba osób jest wymagana" })}
                                    errors={errors}
                                />
                                <InputError errors={errors} name="maxPeople" />
                            </div>

                            <div>
                                <Input
                                    type="text"
                                    name="tripLeader"
                                    placeholder="Przewodnik"
                                    {...register("tripLeader", { required: "Przewodnik jest wymagany" })}
                                    errors={errors}
                                />
                                <InputError errors={errors} name="tripLeader" />
                            </div>

                            <div>
                                <select
                                    className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                                    {...register("active")}
                                >
                                    <option value={true}>Aktywna</option>
                                    <option value={false}>Nieaktywna</option>
                                </select>
                            </div>

                            <button
                                type="submit"
                                className="w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded-lg transition"
                            >
                                Zaktualizuj wycieczkę
                            </button>
                        </form>
                    </div>
                </Modal>

                <Modal show={showBookingsModal} close={closeBookingsModal}>
                    <div ref={modalRef} className="p-6">
                        <h2 className="text-xl font-bold text-gray-800 mb-4">Rezerwacje</h2>

                        {bookings.length === 0 ? (
                            <p className="text-gray-500 italic">Brak rezerwacji dla tej wycieczki.</p>
                        ) : (
                            <ul className="space-y-4">
                                {bookings.map((booking) => (
                                    <li
                                        key={booking.id}
                                        className="border border-gray-200 rounded-xl p-4 shadow-sm bg-white"
                                    >
                                        <div className="text-sm text-gray-700 space-y-1">
                                            <p>
                                                <span className="font-medium text-gray-900">Użytkownik:</span>{" "}
                                                {booking.username}
                                            </p>
                                            <p>
                                                <span className="font-medium text-gray-900">Liczba osób:</span>{" "}
                                                {booking.customers.length || 0}
                                            </p>
                                            <div className="mt-2 pt-2 space-y-1">
                                                {booking.customers.map((customer, index) => (
                                                    <p
                                                        key={index}
                                                        className="text-xs text-gray-600 pl-2"
                                                    >
                                                        - {customer.firstName} {customer.lastName}
                                                    </p>
                                                ))}
                                            </div>
                                        </div>
                                    </li>
                                ))}
                            </ul>
                        )}
                    </div>

                </Modal>
            </div>
        </>
    );
}
