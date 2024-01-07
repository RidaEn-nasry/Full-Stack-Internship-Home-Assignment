

import React, { useState } from 'react';
import Pagination from './Pagination';



const EmployeeTable = ({ employees, currentPage, totalPages, onNext, onPrev }) => {
    return (
        //  if loading shows a skeleton loader

        <div className="flex flex-col items-center justify-center gap-4">
            <h1 className="text-4xl font-bold text-center">Employees</h1>
            <div className="overflow-x-auto">
                <table className="table-auto w-full">
                    <thead className="hidden md:table-header-group">
                        <tr>
                            <th className="px-4 py-2 border bg-gray-800">ID</th>
                            <th className="px-4 py-2 border bg-gray-800 ">Employee Name</th>
                            <th className="px-4 py-2 border bg-gray-800">Job Title</th>
                            <th className="px-4 py-2 border bg-gray-800">Salary</th>
                        </tr>
                    </thead>
                    <tbody>
                        {employees.map((employee) => (
                            <tr key={employee.id} className="md:table-row">
                                <td className="border px-4 py-2 min-w-max block md:table-cell">ID: {employee.id}</td>
                                <td className="border px-4 py-2 min-w-max block md:table-cell">Name: {employee.employeeName}</td>
                                <td className="border px-4 py-2 min-w-max block md:table-cell">Job: {employee.jobTitle}</td>
                                <td className="border px-4 py-2 min-w-max block md:table-cell">Salary: {employee.salary}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                onNext={onNext}
                onPrev={onPrev}
            />
        </div>
    );
};

export default EmployeeTable;

