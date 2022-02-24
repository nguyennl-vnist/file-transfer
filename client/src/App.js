// import React, { Component } from 'react'
// import BasicTabs from './basicTab';

// function App() {
//   return (
//     <BasicTabs />
//   );
// }

// export default App;
import { useState, useEffect } from "react";
import { Navigation } from "./landingPage/navigation";
import { Header } from "./landingPage/header";
import { Features } from "./landingPage/features";
import { About } from "./landingPage/about";
import { Contact } from "./landingPage/contact";
import JsonData from "./data/data.json";
import "./App.css";


const App = () => {
  const [landingPageData, setLandingPageData] = useState({});
  useEffect(() => {
    setLandingPageData(JsonData);
  }, []);

  return (
    <div>
      <Navigation />
      <Header data={landingPageData.Header} />
      <Features data={landingPageData.Features} />
      <About data={landingPageData.About} />
      <Contact data={landingPageData.Contact} />
    </div>
  );
};

export default App;
