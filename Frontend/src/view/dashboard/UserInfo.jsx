import { useContext, useLayoutEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { allCountries } from "country-telephone-data";

import { AuthContext } from "../../component/auth/AuthProvider";
import useUser from "../../hooks/useUser";
import api from "../../services/axios";

import Input from "../../component/Input";
import InputError from "../../component/InputError";
import Loading from "../../component/Loading";
import Error from "../../component/Error";

export default function UserInfo() {
    const { logout } = useContext(AuthContext);
    const navigate = useNavigate();
    const { getUser } = useUser();

    const [dialCode, setDialCode] = useState("");
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);

    const [infoError, setInfoError] = useState('');
    const [infoSuccess, setInfoSuccess] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [passwordSuccess, setPasswordSuccess] = useState('');
    const [deleteError, setDeleteError] = useState('');

    const {
        register: registerInfo,
        handleSubmit: handleSubmitInfo,
        reset: resetInfo,
        formState: { errors: errorsInfo },
    } = useForm({ defaultValues: { username: "", phone: "" } });

    const {
        register: registerPassword,
        handleSubmit: handleSubmitPassword,
        watch: watchPassword,
        reset: resetPassword,
        formState: { errors: errorsPassword },
    } = useForm();

    const { handleSubmit: handleSubmitDelete } = useForm();

    const password = watchPassword("password");

    useLayoutEffect(() => {
        getUser()
            .then((userData) => {
                const country = allCountries.find(c => c.iso2 === userData.country);
                setDialCode(country?.dialCode || "");

                resetInfo({
                    username: userData.username,
                    phone: userData.phoneNumber,
                });

                setError(null);
            })
            .catch(() => {
                setError("Nie udało się pobrać użytkownika.");
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, []);

    const onSubmitInfo = (data) => {
        api.patch("/user/info", {
            username: data.username,
            phone: data.phone,
        })
        .then(() => {
            setInfoSuccess("Dane zmieniono pomyślnie");
            setInfoError("");
            resetInfo(data);
        })
        .catch((error) => {
            const message = error.response?.data?.data?.error || "Coś poszło nie tak podczas aktualizacji danych.";
            setInfoError(message);
            setInfoSuccess("");
        });
    };

    const onSubmitPassword = (data) => {
        api.patch("/user/password", {
            currentPassword: data.currentPassword,
            password: data.password,
        })
        .then(() => {
            setPasswordSuccess("Hasło zmienione pomyślnie");
            setPasswordError("");
            resetPassword();
        })
        .catch((error) => {
            const message = error.response?.data?.data?.error || "Coś poszło nie tak podczas zmiany hasła.";
            setPasswordError(message);
            setPasswordSuccess("");
        });
    };

    const onSubmitDelete = () => {
        api.delete("/user/delete")
            .then(() => {
                logout();
                navigate("/");
            })
            .catch(() => {
                setDeleteError("Coś poszło nie tak podczas usuwania konta.");
            });
    };

    if (isLoading) return <Loading message="Ładowanie danych użytkownika..." />;
    if (error) return <Error message={error} />;

    return (
        <div className="flex sm:justify-center md:justify-start px-4 py-8">
            <div className="bg-white p-6 rounded-2xl shadow-md border border-gray-200 w-full max-w-[500px] text-base">

                <Section title="Dane użytkownika" error={infoError} success={infoSuccess}>
                    <form onSubmit={handleSubmitInfo(onSubmitInfo)} className="space-y-5">
                        <FormGroup label="Nazwa użytkownika">
                            <Input
                                type="text"
                                {...registerInfo("username")}
                                placeholder="Nazwa użytkownika"
                                errors={errorsInfo}
                            />
                            <InputError errors={errorsInfo} name="username" />
                        </FormGroup>

                        <FormGroup label="Telefon">
                            <div className="flex items-center w-full h-[50px] border border-gray-300 rounded-lg px-3 py-2 bg-transparent focus-within:ring-2 focus-within:ring-blue-400 focus-within:border-blue-500 transition duration-200">
                                <span className="text-gray-600 whitespace-nowrap mr-2">+{dialCode}</span>
                                <Input
                                    type="tel"
                                    {...registerInfo("phone", {
                                        required: "Telefon jest wymagany",
                                        pattern: {
                                            value: /^[0-9]{6,15}$/,
                                            message: "Nieprawidłowy numer telefonu",
                                        },
                                    })}
                                    className="flex-1 h-full bg-transparent text-gray-800 placeholder:text-gray-400 outline-none"
                                    placeholder="Numer telefonu"
                                    errors={errorsInfo}
                                />
                            </div>
                            <InputError errors={errorsInfo} name="phone" />
                        </FormGroup>

                        <SubmitButton label="Zaktualizuj dane" />
                    </form>
                </Section>

                <Section title="Zmiana hasła" error={passwordError} success={passwordSuccess}>
                    <form onSubmit={handleSubmitPassword(onSubmitPassword)} className="space-y-5">
                        <FormGroup label="Obecne hasło">
                            <Input
                                type="password"
                                {...registerPassword("currentPassword", { required: "Obecne hasło jest wymagane" })}
                                placeholder="Obecne hasło"
                                errors={errorsPassword}
                            />
                            <InputError errors={errorsPassword} name="currentPassword" />
                        </FormGroup>

                        <FormGroup label="Nowe hasło">
                            <Input
                                type="password"
                                {...registerPassword("password", {
                                    required: "Hasło jest wymagane",
                                    pattern: {
                                        value: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&^_-]).{8,}$/,
                                        message: "Hasło musi mieć 8 znaków, literę, cyfrę i znak specjalny",
                                    },
                                })}
                                placeholder="Nowe hasło"
                                errors={errorsPassword}
                            />
                            <InputError errors={errorsPassword} name="password" />
                        </FormGroup>

                        <FormGroup label="Potwierdź hasło">
                            <Input
                                type="password"
                                {...registerPassword("reTypePassword", {
                                    required: "Potwierdzenie hasła jest wymagane",
                                    validate: value => value === password || "Hasła nie są takie same",
                                })}
                                placeholder="Potwierdź hasło"
                                errors={errorsPassword}
                            />
                            <InputError errors={errorsPassword} name="reTypePassword" />
                        </FormGroup>

                        <SubmitButton label="Zmień hasło" />
                    </form>
                </Section>

                <Section title="Usuń konto" error={deleteError}>
                    <form onSubmit={handleSubmitDelete(onSubmitDelete)} className="space-y-5">
                        <button
                            type="submit"
                            className="w-full py-3 rounded-lg bg-red-600 hover:bg-red-700 text-white font-semibold transition"
                        >
                            Usuń konto
                        </button>
                    </form>
                </Section>

            </div>
        </div>
    );
}

const Section = ({ title, error, success, children }) => (
    <div className="mb-10">
        <h2 className="text-xl font-semibold mb-4">{title}</h2>
        {error && <div className="text-red-500 text-center mb-4">{error}</div>}
        {success && <div className="text-green-500 text-center mb-4">{success}</div>}
        {children}
    </div>
);

const FormGroup = ({ label, children }) => (
    <div>
        <label className="block text-sm font-medium mb-1">{label}</label>
        {children}
    </div>
);

const SubmitButton = ({ label }) => (
    <button
        type="submit"
        className="w-full py-3 rounded-lg bg-blue-600 hover:bg-blue-700 text-white font-semibold transition"
    >
        {label}
    </button>
);
