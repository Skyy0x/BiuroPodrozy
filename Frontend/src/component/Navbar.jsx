import { useState } from 'react';
import { Menu, X } from 'lucide-react';
import useRole from '../hooks/useRole';

export default function Navbar() {
    const userRole = useRole();
    const [isOpen, setIsOpen] = useState(false);

    return (
        <nav className="bg-white">
            <div className="flex items-center justify-between py-4">
                <a href="/" className="text-2xl font-bold">
                    <span>Podroze</span>
                    <span className="text-blue-500">Marzen</span>
                </a>

                <div className="md:hidden">
                    <button onClick={() => setIsOpen(!isOpen)}>
                        {isOpen ? <X size={28} /> : <Menu size={28} />}
                    </button>
                </div>

                <ul className="hidden md:flex space-x-6 font-bold text-black">
                    <li><a href="#" className="hover:text-blue-900">Wycieczki</a></li>
                    <li><a href="#" className="hover:text-blue-900">O nas</a></li>
                    <li><a href="#" className="hover:text-blue-900">Kontakt</a></li>
                </ul>

                <div className="hidden md:block font-bold space-x-2">
                    {userRole === "ADMIN" ? (
                        <a href="/dashboard" className='hover:text-blue'>Panel administratora</a>
                    ) : userRole === "USER" ? (
                        <a href="/dashboard" className='hover:text-blue'>Panel Uzytkonika</a>
                    ) : (
                        <>
                            <a href="/login" className='hover:text-blue'>Logowanie</a>
                            <a href="/register" className='hover:text-blue'>Rejestracja</a>
                        </>
                    )}
                </div>

            </div>

            {isOpen && (
                <div className="md:hidden px-6 pb-4">
                    <ul className="space-y-3 font-bold text-black">
                        <li><a href="#" className="block hover:text-blue-900">Wycieczki</a></li>
                        <li><a href="#" className="block hover:text-blue-900">O nas</a></li>
                        <li><a href="#" className="block hover:text-blue-900">Kontakt</a></li>
                        <li><a href="#" className="block hover:text-blue-900">Konto</a></li>
                        {userRole === "ADMIN" ? (
                            <li><a href="/dashboard" className='hover:text-blue'>Panel administratora</a></li>
                        ) : userRole === "USER" ? (
                            <li><a href="/dashboard" className='hover:text-blue'>Panel Uzytkonika</a></li>
                        ) : (
                            <>
                                <li><a href="/login" className='hover:text-blue'>Logowanie</a></li>
                                <li><a href="/register" className='hover:text-blue'>Rejestracja</a></li>
                            </>
                        )}
                    </ul>
                </div>
            )}
        </nav>
    );
}