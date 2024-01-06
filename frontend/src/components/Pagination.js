

import React from "react";

const Pagination = ({
    paginate, currentPage, pageNumbers
}) => {

    return (
        <nav className="flex flex-row items-center justify-center">
            <button
                onClick={() => paginate(currentPage - 1)}
                disabled={currentPage === 1}
                className="px-4 py-2 mx-2 text-white bg-blue-500 rounded hover:bg-blue-700"
            >
                Prev
            </button>
            {pageNumbers.map((number) => (
                <button
                    key={number}
                    onClick={() => paginate(number)}
                    className={`md:px-4 px-2 py-2 mx-2 ${currentPage === number ? 'bg-blue-700' : 'bg-blue-500'} text-white rounded hover:bg-blue-700`}
                >
                    {number}
                </button>
            ))
            }
            < button
                onClick={() => paginate(currentPage + 1)}
                disabled={currentPage === pageNumbers[pageNumbers.length - 1]}
                className="px-4 py-2 mx-2 text-white bg-blue-500 rounded hover:bg-blue-700"
            >
                Next
            </button >
        </nav >
    );
};
export default Pagination;




