

import React, { useState } from 'react';
import Pagination from './Pagination';
import usePaginate from '@/hooks/usePaginate';


const EmployeeTable = ({ employees }) => {

    const employeesPerPage = 10;
    const { currentPage, paginate, pageNumbers } = usePaginate(employees.length, employeesPerPage);

    // get slice of 10 employees we're currrently at
    const currentEmployees = employees.slice(
        (currentPage - 1) * employeesPerPage,
        currentPage * employeesPerPage,
    );


    return (
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
                        {currentEmployees.map((employee) => (
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
                paginate={paginate}
                currentPage={currentPage}
                pageNumbers={pageNumbers}

            />

        </div >
    )

}

export default EmployeeTable;

export async function getServerSideProps() {
    const res = await fetch('http://localhost:3000/api/employees')
    const employees = await res.json()

    return {
        props: {
            employees,
        },
    }
}

// export async function getStaticProps() {

//     const res = await fetch('http://localhost:3000/api/employees')