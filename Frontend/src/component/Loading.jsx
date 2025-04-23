export default function Loading({message}) {
    return (
        <div className="w-full h-full flex justify-center items-center">
            <p className="text-xl font-bold">
                {message}
            </p>
        </div>
    )
}