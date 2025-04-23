import React, { useState, useEffect } from 'react';

export default function Modal({ children, show = false, close = () => {}}) {
    const closeModal = () => {
        close();
    };

    return (
        <div>
            {show && (
                <div onClick={closeModal}>
                    <div className="fixed inset-0 flex justify-center z-10 h-full">
                        <div className="absolute inset-0"></div>
                        <div onClick={e => {e.stopPropagation()}} className="z-10 bg-white p-6 rounded-lg shadow-lg h-max">
                            {children}
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};