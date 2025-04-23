import formatDate from "../../utils/formatDate"

export default function Booking({booking}) {
    return (
        <>
            <div className="flex flex-col p-3 rounded-sm gap-2 border-[0.5px] border-gray-300 w-full sm:w-70 md:w-82 lg:w-96">
                <div className="flex h-60">
                    <img className="object-cover rounded-md w-full h-full" src="../gerber.jpg" alt="trip image" />
                </div>

                <div className="flex flex-row">
                    <div className="w-full">
                        <h5 className="text-lg font-semibold">
                            {booking.trip.name}
                        </h5>

                        <div className="text-sm">
                            <p className="font-semibold">
                                Calkowity koszt: <span className="font-light">
                                    {booking.trip.pricePerPerson * booking.customers.length} PLN
                                </span>
                            </p>
                        </div>

                        <span className="flex text-xs text-gray-500">
                            {formatDate(booking.trip.startDate)} - {formatDate(booking.trip.endDate)}
                        </span>
                    </div>
                    <div className="text-xs font-medium text-gray-600 flex flex-col items-end w-full">
                        <p>
                            Osoby:
                        </p>
                        {booking.customers.map((customer, index) => {
                            return (
                                <div key={index}>
                                    <p>
                                        {customer.firstName} {customer.lastName}
                                    </p>
                                </div>
                            )
                        })}
                    </div>
                </div>
            </div>
        </>
    )
}