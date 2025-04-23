export default function InputError({errors, name}) {
    return (
        <>
            {errors?.[name] && (
                <p className="text-sm text-red-600 mt-1">{errors[name].message || "This field is required"}</p>
            )}
        </>
    )
}