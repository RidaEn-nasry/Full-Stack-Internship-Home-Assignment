import { Inter } from 'next/font/google'

const inter = Inter({ subsets: ['latin'] })
import { useEffect, useState } from 'react';

import EmployeeTable from '@/components/EmployeeTable';
import JobSummaryTable from '@/components/JobSummaryTable';


import { FaUpload, FaFileAlt, FaArrowRight } from 'react-icons/fa';

export default function Home() {

  const [selectedFile, setSelectedFile] = useState(null);

  const [employees, setEmployees] = useState([]);
  const [averageSalaryByJobTitle, setAverageSalaryByJobTitle] = useState({});


  const saveToSessionStorage = (key, value) => {

    sessionStorage.setItem(key, JSON.stringify(value));
  }

  const getFromSessionStorage = (key) => {

    const value = sessionStorage.getItem(key);
    if (value) return JSON.parse(value);
    return null;
  }



  // a function to in-memory / local storage state
  const resetState = () => {
    saveToSessionStorage('employees', []);
    saveToSessionStorage('averageSalaryByJobTitle', {});
    setEmployees([]);
    setAverageSalaryByJobTitle({});
  }



  const changeHandler = (event) => {
    // if there's a file, set it to selectedFile
    if (event.target.files.length > 0) {
      setSelectedFile(event.target.files[0]);
      setShowProcessButton(true);

      // reset the state
      resetState();

    }
  }

  const [showProcessButton, setShowProcessButton] = useState(false);


  // if we have data in local storage, set it to state
  useEffect(() => {
    const employees = getFromSessionStorage('employees');
    const averageSalaryByJobTitle = getFromSessionStorage('averageSalaryByJobTitle');
    const selectedFile = getFromSessionStorage('selectedFile');
    if (employees) setEmployees(employees);
    if (averageSalaryByJobTitle) setAverageSalaryByJobTitle(averageSalaryByJobTitle);
    setLoading(false);
  }, []);


  const handleFileUpload = () => {
    if (!selectedFile) return;
    const baseApiUrl = process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080/api";
    const uploadEndpoint = `${baseApiUrl}/employees/upload`;

    const formData = new FormData();
    formData.append('file', selectedFile);
    fetch(uploadEndpoint, {
      method: 'POST',
      body: formData
    })
      .then(response => response.json())
      .then(result => {
        if (result.error) {
          alert(result.message);
          resetState();
          setSelectedFile(null);
          return;
        }


        console.log('Success:', result);
        console.log(result);
        setEmployees(result['employees']);
        setAverageSalaryByJobTitle(result['averageSalaryByJobTitle']);
        // save to local storage
        saveToSessionStorage('employees', result['employees']);
        saveToSessionStorage('averageSalaryByJobTitle', result['averageSalaryByJobTitle']);
        setLoading(false);

      })
      .catch(error => {
        console.error('Error:', error);
        // reset the state
        alert("Error: " + error);
        resetState();
        setSelectedFile(null);
      });

    setShowProcessButton(false);
  }

  const [loading, setLoading] = useState(true);


  return (

    loading ?
      <></>
      :
      <main className="flex flex-col items-center justify-center min-h-screen p-10 bg-gray-900 text-white">
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
          {employees.length > 0 && <EmployeeTable employees={employees} />}
          {employees.length > 0 && <hr className="w-full border-gray-700" />}
          {Object.keys(averageSalaryByJobTitle).length > 0 && <JobSummaryTable averageSalaryByJobTitle={averageSalaryByJobTitle} />}
        </div>
      </main>

  );
};
