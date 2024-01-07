
const JobSummaryTable = ({ averageSalaryByJobTitle }) => {
    return (
        <div className="flex flex-col items-center justify-center gap-4">
            <h1 className="text-4xl font-bold text-center ">Average Salary By Job Title</h1>
            <table className="table-auto w-full">
                <thead>
                    <tr>
                        <th className="px-4 py-2 border bg-gray-800">Job Title</th>
                        <th className="px-4 py-2 border bg-gray-800">Average Salary</th>
                        <th className="px-4 py-2 border bg-gray-800">Employee Count</th>
                    </tr>
                </thead>
                <tbody>
                    {averageSalaryByJobTitle.map((entry) => (
                        <tr key={entry.jobTitle}>
                            <td className="border px-4 py-2">{entry.jobTitle}</td>
                            <td className="border px-4 py-2">${entry.averageSalary.toFixed(2)}</td>
                            <td className="border px-4 py-2">{entry.employeeCount}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default JobSummaryTable;


