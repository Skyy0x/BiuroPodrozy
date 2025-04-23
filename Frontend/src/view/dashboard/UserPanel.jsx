import { Menu, X } from "lucide-react";
import { useLayoutEffect, useState } from "react";
import PagePadding from "../../component/PagePadding";
import UserBookings from "./UserBookings";
import UserInfo from "./UserInfo";
import UserHistory from "./UserHistory";

export default function UserPanel() {
    const [isOpen, setIsOpen] = useState(false);
    const [activeIndex, setActiveIndex] = useState(0);
    const [hasMounted, setHasMounted] = useState(false);

    useLayoutEffect(() => {
        const savedIndex = localStorage.getItem("userPanelTab");
        if (savedIndex !== null) {
            setActiveIndex(parseInt(savedIndex));
        }
        setHasMounted(true);
    }, []);
    
    const handleTabChange = (tab) => {
        setActiveIndex(tab);
        localStorage.setItem("userPanelTab", tab);
        setIsOpen(false);
    };

    const items = [
        { id: 0, content: <UserBookings />, name: "Moje wycieczki" },
        { id: 1, content: <UserInfo />, name: "Moje dane" },
        { id: 2, content: <UserHistory />, name: "Historia wycieczek" },
    ];

    return (
        <PagePadding>
            <div className="min-h-screen flex flex-col">
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
                            {items.map((tab, index) => (
                                <li key={index}>
                                    <button
                                        onClick={() => handleTabChange(index)}
                                        className={`hover:text-blue-900 ${activeIndex === index ? "text-blue-500" : ""}`}
                                    >
                                        {tab.name}
                                    </button>
                                </li>
                            ))}
                        </ul>

                        <div className="hidden md:block">
                            <a href="/logout" className="font-bold hover:text-blue-900">
                                Wyloguj sie
                            </a>
                        </div>
                    </div>

                    {isOpen && (
                        <div className="md:hidden px-6 pb-4">
                            <ul className="space-y-3 font-bold text-black">
                                {items.map((tab, index) => (
                                    <li key={index}>
                                        <button
                                            onClick={() => handleTabChange(index)}
                                            className="block w-full text-left hover:text-blue-900"
                                        >
                                            {tab.name}
                                        </button>
                                    </li>
                                ))}
                            </ul>
                        </div>
                    )}
                </nav>

                <div className="relative w-full flex-1 overflow-auto">
                    <div
                        className={`flex ${hasMounted ? "transition-transform duration-500 ease-in-out" : ""}`}
                        style={{ transform: `translateX(-${activeIndex * 100}%)` }}
                        >
                            {items.map((item) => (
                                <div
                                    key={item.id}
                                    className="w-full flex-shrink-0 px-4 py-6"
                                >
                                    {item.content}
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
        </PagePadding>
    );
}
