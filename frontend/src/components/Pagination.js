

import React from "react";

const Pagination = ({ currentPage, totalPages, onNext, onPrev }) => {
    return (
        <nav className="flex flex-row items-center justify-center">
            <button
                onClick={onPrev}
                disabled={currentPage === 1}
                className="px-4 py-2 mx-2 text-white bg-blue-500 rounded hover:bg-blue-700"
            >
                Prev
            </button>
            <span>Page {currentPage} of {totalPages}</span>
            <button
                onClick={onNext}
                disabled={currentPage >= totalPages}
                className="px-4 py-2 mx-2 text-white bg-blue-500 rounded hover:bg-blue-700"
            >
                Next
            </button>
        </nav>
    );
};
export default Pagination;



