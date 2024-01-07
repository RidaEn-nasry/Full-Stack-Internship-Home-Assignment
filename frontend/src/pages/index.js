import { Inter } from 'next/font/google'

import { useEffect, useState } from 'react';

import EmployeeTable from '@/components/EmployeeTable';
import JobSummaryTable from '@/components/JobSummaryTable';

import { FaUpload, FaFileAlt, FaArrowRight } from 'react-icons/fa';
import LoadingSpinner from '@/components/LoadingSpinner';


export default function Home() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [showProcessButton, setShowProcessButton] = useState(false);
  const [employees, setEmployees] = useState([]);
  const [averageSalaryByJobTitle, setAverageSalaryByJobTitle] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize, setPageSize] = useState(10); // Assuming a constant page size for now
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  /*
  Load average salaries/employees from session storage only once when the component mounts.
  to one: prevent data loss, and two: to avoid unnecessary requests
  */
  useEffect(() => {
    const savedEmployees = sessionStorage.getItem('employees');
    const avgSalaries = sessionStorage.getItem('averageSalaries');
    const savedCurrentPage = sessionStorage.getItem('currentPage');
    const savedTotalPages = sessionStorage.getItem('totalPages');

    if (savedCurrentPage) {
      setCurrentPage(JSON.parse(savedCurrentPage));
    }

    if (savedTotalPages) {
      setTotalPages(JSON.parse(savedTotalPages));
    }

    if (savedEmployees) {
      setEmployees(JSON.parse(savedEmployees));
    }

    if (avgSalaries) {
      setAverageSalaryByJobTitle(JSON.parse(avgSalaries));
    }

    setLoading(false);
  }, []);


  const handleFileUpload = async () => {
    if (!selectedFile) return;

    try {
      setLoading(true);

      const baseApiUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api";
      const uploadEndpoint = `${baseApiUrl}/employees/upload`;

      const formData = new FormData();
      formData.append('file', selectedFile);

      const response = await fetch(uploadEndpoint, {
        method: 'POST',
        body: formData,
      });

      const result = await response.json();
      if (result.error) {
        throw new Error(result.message);
      }

      setEmployees(result.employees.content);
      setCurrentPage(result.employees.pageable.pageNumber);
      setTotalPages(result.employees.totalPages);

      sessionStorage.setItem('averageSalaries', JSON.stringify(result.averageSalaryByJobTitles));
      setAverageSalaryByJobTitle(result.averageSalaryByJobTitles);
    } catch (error) {
      console.error('Error:', error);
      alert("Error: " + error.message);
    } finally {
      setSelectedFile(null);
      setShowProcessButton(false);
      setLoading(false);
    }
  };


  const handleNextPage = async () => {
    const newPage = currentPage + 1;
    if (currentPage < totalPages - 1) {
      await fetchEmployees(newPage);
    }
  };


  const handlePrevPage = async () => {
    const newPage = currentPage - 1;
    if (currentPage > 0) {
      await fetchEmployees(newPage);
    }
  };

  const fetchEmployees = async (page) => {
    if (page < 0 || page >= totalPages) return;
    try {
      const baseApiUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api";
      const employeesEndpoint = `${baseApiUrl}/employees?page=${page}&size=${pageSize}`;

      const response = await fetch(employeesEndpoint);
      const result = await response.json();
      if (result.error) {
        throw new Error(result.message);
      }

      setEmployees(result.content);
      setCurrentPage(result.pageable.pageNumber);
      setTotalPages(result.totalPages);

      // Save to session storage
      sessionStorage.setItem('currentPage', JSON.stringify(page));
      sessionStorage.setItem('employees', JSON.stringify(result.content));
      sessionStorage.setItem('totalPages', JSON.stringify(result.totalPages));
    } catch (error) {
      console.error('Error:', error);
      alert("Error: " + error.message);
    }
  };


  const changeHandler = (event) => {
    if (event.target.files.length > 0) {
      setSelectedFile(event.target.files[0]);
      setShowProcessButton(true);
    }
  };


  return (
    loading ? <LoadingSpinner /> :
      <main className="flex flex-col items-center justify-center min-h-screen p-10 bg-gray-900 text-white" >
        <h1 className="text-5xl font-bold mb-10 text-center">Employee Data Upload</h1>
        <div className="flex flex-col items-center justify-center space-y-5">
          <div className="flex flex-col items-center space-y-5">
            <label htmlFor="file" className="flex items-center space-x-2 px-4 py-2 bg-blue-500 rounded-lg text-white cursor-pointer hover:bg-blue-700">
              <FaUpload />
              <span>Upload CSV</span>
            </label>
            <input type="file" id="file" name="file" accept=".csv" onChange={changeHandler} className="hidden" />
            {selectedFile && (
              <div className="flex items-center space-x-2">
                <FaFileAlt className="text-blue-500" />
                <span className="text-gray-300">{selectedFile.name}</span>
              </div>
            )}
          </div>
          {showProcessButton && (
            <div className="flex items-center space-x-2 mt-4">
              <button onClick={handleFileUpload} className="flex items-center space-x-2 px-4 py-2 bg-green-500 rounded-lg text-white hover:bg-green-700">
                <span>Process</span>
                <FaArrowRight />
              </button>
            </div>
          )}

          {employees.length > 0 &&
            <EmployeeTable
              employees={employees}
              currentPage={currentPage}
              totalPages={totalPages}
              onNext={handleNextPage}
              onPrev={handlePrevPage}
            />
          }
          {employees.length > 0 && <hr className="w-full border-gray-700" />}
          {averageSalaryByJobTitle.length > 0 &&
            <JobSummaryTable
              averageSalaryByJobTitle={averageSalaryByJobTitle}
            />
          }

        </div>
      </main >

  );
}
