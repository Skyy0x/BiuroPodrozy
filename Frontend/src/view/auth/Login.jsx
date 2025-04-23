import { useState } from "react";
import { useForm } from "react-hook-form";
import api from "../../services/axios";
import Input from "../../component/Input";
import InputError from "../../component/InputError";

export default function Login() {
    const {register, handleSubmit, reset, formState: {errors}} = useForm();
    const [error, setError] = useState('');

    const onLogin = (data) => {
        api.post('/auth/login', {
            username: data.username,
            password: data.password
        })
        .then(response => {
            sessionStorage.setItem('token', response.data.data.token.token);

            window.location.href = "/dashboard";
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
                <h1 className="text-black font-semibold text-[36px] leading-[2.5rem] text-center mb-[20px]">Zaloguj się</h1>
                <form onSubmit={handleSubmit(onLogin)}>
                    {error && (
                        <div className="text-red-500 text-center mb-4">
                            {error}
                        </div>
                    )}
                    <div className="flex flex-col w-full h-auto mb-[20px]">
                        <Input 
                            type="text"
                            name="username"
                            {...register("username", { 
                                required: "Nazwa uzytkownika jest wymagana" 
                            })}
                            placeholder="Nazwa uzytkownika"
                            errors={errors}
                        />
                        <InputError
                            name="username"
                            errors={errors}
                        />
                    </div>
                    <div className="flex flex-col w-full h-auto mb-[20px]">
                        <Input 
                            type="password"
                            name="password"
                            {...register("password", { 
                                required: "Haslo jest wymagane" 
                            })}
                            placeholder="Haslo"
                            errors={errors}
                        />
                        <InputError
                            name="password"
                            errors={errors}
                        />
                    </div>
                    <button type="submit" 
                        className="w-full h-[45px] bg-blue-600 text-white font-semibold rounded-full shadow-lg hover:bg-blue-500 transition duration-300">
                        Zaloguj
                    </button>
                    <div className="text-[14px] text-center mt-[20px] text-gray-600">
                        <p>
                            Nie posiadasz konta?
                            <a href="/register" className="mx-[5px] font-semibold text-blue-600 hover:text-blue-800 transition duration-200">
                                Zarejestruj się
                            </a>
                        </p>
                    </div>
                </form>
            </div>
        </div>
    )
}