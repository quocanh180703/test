import logo from "./logo.svg";
import "./App.css";
import React, { useEffect, useState } from "react";

function App() {
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8000/api/v1/categories") // Gọi API từ backend
        .then(response => response.json())
        .then(data => setCategories(data))
        .catch(error => console.error("Lỗi khi gọi API:", error));
  }, []);

  return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <p>
            Edit <code>src/App.js</code> and save to reload.
          </p>
          <a
              className="App-link"
              href="https://reactjs.org"
              target="_blank"
              rel="noopener noreferrer"
          >
            Learn React
          </a>
        </header>
        <div>
          <h1>Danh mục khóa học</h1>
          <ul>
            {categories.map((category) => (
                <li key={category.id}>{category.name}</li>
            ))}
          </ul>
        </div>
      </div>
  );
}

export default App;
