import React from "react";

const Input = React.forwardRef(({ type = "text", name, errors, className, ...rest }, ref) => {
    return (
        <input
            id={name}
            name={name}
            type={type}
            ref={ref}
            className={className ? className : `w-full px-4 py-3 border ${
                errors?.[name] ? "border-red-500" : "border-gray-300"
            } rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400`}
            {...rest}
        />
    );
});

export default Input;
