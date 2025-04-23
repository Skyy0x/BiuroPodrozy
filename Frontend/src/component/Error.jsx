export default function Error({message}) {
    return (
        <div className="w-full h-full">
            <p className="text-xl font-bold text-red-600">
                {message}
            </p>
        </div>
    )
}