


const LoadingSpinner = () => (
    <div className="flex justify-center items-center w-screen h-screen bg-gray-900 text-white">

        <div className="flex flex-col items-center space-y-5">
            <div className="flex flex-col items-center space-y-5">
                <div className="flex items-center space-x-2">
                    <svg className="animate-spin h-5 w-5 mr-3 ..." viewBox="0 0 24 24">
                        <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                        <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8"></path>
                    </svg>
                    <span>Loading...</span>
                </div>
            </div>

        </div>
    </div>
);


export default LoadingSpinner;