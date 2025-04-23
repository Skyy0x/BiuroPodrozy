import axios from "axios";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { allCountries } from 'country-telephone-data';
import api from "../../services/axios";
import Input from "../../component/Input";
import InputError from "../../component/InputError";

export default function Register() {
    const {register, handleSubmit, watch, reset, formState: { errors }} = useForm();
    const [error, setError] = useState('');
    const [dialCode, setDialCode] = useState("");
    const password = watch("password");
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&^_-])[A-Za-z\d@$!%*#?&^_-]{8,}$/;  

    const onRegister = (data) => {
        api.post('/auth/register', {
            username: data.username,
            password: data.password,
            email: data.email,
            phone: data.phone,
            country: data.country
        })
        .then(response => {
            sessionStorage.setItem('token', response.data.data.token.token);
            window.location.href = '/dashboard';
        })
        .catch(error => {
            const message = error.response?.data?.data?.error;
            if (message) {
                setError(message);
            } else {
                setError("Nieoczekiwany błąd po stronie serwera.");
            }
        });
    
        reset();
    }

    return (
        <div className="wrapper flex h-screen justify-center items-center bg-gradient-to-r from-blue-300 to-blue-600">
            <div className="w-[420px] bg-white text-gray-800 rounded-lg shadow-xl px-[30px] py-[40px] border border-gray-300">
                <h1 className="text-black font-semibold text-[36px] leading-[2.5rem] text-center mb-[20px]">Zarejestruj sie</h1>
                <form onSubmit={handleSubmit(onRegister)}>
                    {error && (
                        <div className="text-red-500 text-center mb-4">
                            {error}
                        </div>
                    )}
                    <div className="w-full mb-[30px]">
                        <Input
                            type="text"
                            name="username"
                            {...register("username", { 
                                required: "Nazwa uzytkownika jest wymagana" 
                            })}
                            placeholder="Nazwa użytkownika"
                            errors={errors}
                        />
                        <InputError
                            name="username"
                            errors={errors}
                        />
                    </div>
                    <div className="w-full my-[30px]">
                        <Input
                            type="email"
                            name="email"
                            {...register("email", { 
                                required: "Email jest wymagany" 
                            })}
                            placeholder="Email"
                            errors={errors}
                        />
                        <InputError
                            name="email"
                            errors={errors}
                        />
                    </div>
                    <div className="w-full my-[30px]">
                        <select {...register("country", { 
                                required: "Kraj jest wymagany" 
                            })}
                            className="w-full h-full text-gray-800 placeholder:text-gray-400 bg-transparent border-[1px] border-gray-300 outline-none rounded-lg px-[20px] py-[15px] focus:ring-2 focus:ring-blue-400 focus:border-blue-500 transition duration-200"
                            onChange={(e) => {
                                const selected = allCountries.find(c => {
                                    return c.iso2 === e.target.value
                                });
                                setDialCode(selected?.dialCode || "");
                              }}
                            >
                            <option value="">Wybierz kraj</option>
                            {allCountries.map((country) => (
                                <option key={country.iso2} value={country.iso2}>
                                    {country.name}
                                </option>
                            ))}
                        </select>
                        {errors.country && (
                            <p className="text-red-500 text-sm mt-1">{errors.country.message}</p>
                        )}
                    </div>
                    <div className="w-full my-[30px]">
                        <div
                            className={`flex items-center w-full h-[50px] border rounded-lg px-3 py-2 bg-transparent focus-within:ring-2 focus-within:ring-blue-400 focus-within:border-blue-500 transition duration-200 ${
                                errors.phone ? "border-red-500" : "border-gray-300"
                            }`}
                        >
                            <span className="text-gray-600 whitespace-nowrap mr-2">+{dialCode}</span>

                            <Input
                                type="tel"
                                name="phone"
                                {...register("phone", { 
                                    required: "Telefon jest wymagany",
                                    pattern: {
                                        value: /^[0-9]{6,15}$/,
                                        message: "Nieprawidłowy numer telefonu"
                                    }
                                })}
                                className="flex-1 h-full bg-transparent text-gray-800 placeholder:text-gray-400 outline-none"
                                placeholder="Numer telefonu"
                                errors={errors}
                                disabled={!dialCode}
                            />
                        </div>
                        <InputError
                            name="phone"
                            errors={errors}
                        />
                    </div>
                    <div className="w-full my-[30px]">
                        <Input
                            type="password"
                            name="password"
                            {...register("password", { 
                                required: "Haslo jest wymagane",
                                pattern: {
                                    value: passwordRegex,
                                    message: "Hasło musi zawierać 8 znaków, literę, cyfrę i znak specjalny"
                                }
                            })}
                            placeholder="Haslo"
                            errors={errors}
                        />
                        <InputError
                            name="password"
                            errors={errors}
                        />
                    </div>
                    <div className="w-full my-[30px]">
                        <Input
                            type="password"
                            name="reTypePassword"
                            {...register("reTypePassword", { 
                                required: "Potwierdzenie hasla jest wymagane",
                                validate: value => value === password || "Hasla nie sa takie same"
                            })}
                            placeholder="Haslo"
                            errors={errors}
                        />
                        <InputError
                            name="reTypePassword"
                            errors={errors}
                        />
                    </div>
                    <button type="submit" 
                        className="w-full h-[45px] bg-blue-600 text-white font-semibold rounded-full shadow-lg hover:bg-blue-500 transition duration-300">
                        Zarejestruj sie
                    </button>
                    <div className="text-[14px] text-center mt-[20px] text-gray-600">
                        <p>
                            Posiadasz konto?
                            <a href="/login" className="mx-[5px] font-semibold text-blue-600 hover:text-blue-800 transition duration-200">
                                Zaloguj sie
                            </a>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    )
}