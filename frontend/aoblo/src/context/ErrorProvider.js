import React, { createContext, useState, useEffect } from 'react';
import Papa from 'papaparse';

export const ErrorContext = createContext();

export const ErrorProvider = ({ children }) => {
  const [errors, setErrors] = useState({});

  useEffect(() => {
    Papa.parse('/errors.csv', {
      download: true,
      header: true,
      complete: (result) => {
        const errorMap = {};
        result.data.forEach((row) => {
          errorMap[row.code] = row.message;
        });
        setErrors(errorMap);
      },
      error: (error) => console.error('Error loading CSV:', error),
    });
  }, []);

  return (
    <ErrorContext.Provider value={errors}>
      {children}
    </ErrorContext.Provider>
  );
};
