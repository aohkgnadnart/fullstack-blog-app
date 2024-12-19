import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';
import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import { ErrorProvider } from './context/ErrorProvider';

ReactDOM.render(
  <ErrorProvider>
    <App />
  </ErrorProvider>,
  document.getElementById('root')
);
