import { useForm } from "react-hook-form";
import Input from "../../component/Input";
import InputError from "../../component/InputError";
import api from "../../services/axios";
import { useState } from "react";

export default function AdminCreateTrip() {
    const {
        register,
        handleSubmit,
        formState: { errors },
        reset
    } = useForm();

    const [submitError, setSubmitError] = useState(null);

    const onSubmit = (data) => {
        api.post("/manage/trips", {
            ...data,
            pricePerPerson: parseFloat(data.pricePerPerson),
            maxPeople: parseInt(data.maxPeople),
            active: data.active === "true" || data.active === true,
        })
            .then(() => {
                reset();
                localStorage.setItem("adminPanelTab", 0);
                window.location.reload();
            })
            .catch(() => {
                setSubmitError("Nie udało się utworzyć wycieczki.");
            });
    };

    return (
        <div className="max-w-2xl mx-auto bg-white border border-gray-200 rounded-2xl shadow-lg p-8">
            <h2 className="text-2xl font-bold text-gray-800 mb-6">Utwórz nową wycieczkę</h2>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-5">
                {submitError && (
                    <div className="bg-red-100 text-red-700 p-3 rounded-md text-sm">
                        {submitError}
                    </div>
                )}

                <div>
                    <Input
                        type="text"
                        name="name"
                        placeholder="Nazwa wycieczki"
                        {...register("name", { required: "Nazwa jest wymagana" })}
                        errors={errors}
                    />
                    <InputError errors={errors} name="name" />
                </div>

                <div>
                    <Input
                        type="text"
                        name="description"
                        placeholder="Opis wycieczki"
                        {...register("description", { required: "Opis jest wymagany" })}
                        errors={errors}
                    />
                    <InputError errors={errors} name="description" />
                </div>

                <div className="grid grid-cols-2 gap-4">
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

                    <div>
                        <Input
                            type="number"
                            name="maxPeople"
                            placeholder="Maks. liczba osób"
                            min="1"
                            {...register("maxPeople", { required: "Maksymalna liczba osób jest wymagana" })}
                            errors={errors}
                        />
                        <InputError errors={errors} name="maxPeople" />
                    </div>
                </div>

                <div className="grid grid-cols-2 gap-4">
                    <div>
                        <Input
                            type="date"
                            name="startDate"
                            {...register("startDate", { required: "Data rozpoczęcia jest wymagana" })}
                            errors={errors}
                        />
                        <InputError errors={errors} name="startDate" />
                    </div>

                    <div>
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
                        type="text"
                        name="tripLeader"
                        placeholder="Przewodnik"
                        {...register("tripLeader", { required: "Przewodnik jest wymagany" })}
                        errors={errors}
                    />
                    <InputError errors={errors} name="tripLeader" />
                </div>

                <div>
                    <label className="block mb-1 font-medium text-gray-700">Status wycieczki</label>
                    <select
                        className="w-full px-4 py-2 border border-gray-300 rounded-lg bg-white focus:outline-none focus:ring-2 focus:ring-blue-400"
                        {...register("active")}
                        defaultValue="true"
                    >
                        <option value="true">Aktywna</option>
                        <option value="false">Nieaktywna</option>
                    </select>
                </div>

                <button
                    type="submit"
                    className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-xl transition duration-200"
                >
                    Utwórz wycieczkę
                </button>
            </form>
        </div>
    );
}

