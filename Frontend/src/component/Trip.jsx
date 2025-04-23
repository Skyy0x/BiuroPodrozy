import { useNavigate } from "react-router-dom";
import formatDate from "../utils/formatDate";

export default function Trip({trip}) {
    const navigate = useNavigate();

    return (
        <>
            <div className="flex flex-col p-3 rounded-sm gap-2 border-[0.5px] border-gray-300 w-full sm:w-60 md:w-68 lg:w-72">
                <div className="flex h-50">
                    <img className="object-cover rounded-md w-full h-full" src="../gerber.jpg" alt="trip image" />
                </div>

                <div className="flex flex-row justify-between mt-2">
                    <h5 className="text-lg font-semibold">
                        {trip.name}
                    </h5>

                    <div>
                        <span className="font-semibold">
                            {trip.pricePerPerson} PLN
                        </span>
                        <span className="font-light text-sm">
                            /osoba
                        </span>
                    </div>
                </div>

                <div className="flex justify-between items-center mt-2">
                    <span className="flex text-xs text-gray-500">
                        {formatDate(trip.startDate)} - {formatDate(trip.endDate)}
                    </span>

                    <button onClick={() => navigate(`/trip/${trip.id}`)} className="flex bg-blue-500 text-white py-1 px-4 rounded-md hover:bg-blue-400 text-sm">
                        Sprawdz
                    </button>
                </div>
            </div>
        </>
    );
}