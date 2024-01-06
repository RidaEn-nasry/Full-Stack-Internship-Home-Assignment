
const JobSummaryTable = ({ averageSalaryByJobTitle }) => {
    return (
        <div className="flex flex-col items-center justify-center gap-4">
            <h1 className="text-4xl font-bold text-center ">Average Salary By Job Title</h1>


            <table className="table-auto w-full">
                <thead>
                    <tr>
                        <th className="px-4 py-2 border bg-gray-800">Job Title</th>
                        <th className="px-4 py-2 border bg-gray-800">Average Salary</th>
                    </tr>
                </thead>
                <tbody>
                    {Object.keys(averageSalaryByJobTitle).map((jobTitle, index) => (
                        <tr key={index}>
                            <td className="border px-4 py-2">{jobTitle}</td>
                            <td className="border px-4 py-2">{averageSalaryByJobTitle[jobTitle]}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default JobSummaryTable;


