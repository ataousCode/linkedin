import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./assets/pages/Login/Login";
import Registration from "./assets/pages/Registration/Registration";
import ResetPassword from "./assets/pages/ResetPassword/ResetPassword";
import VerifyEmail from "./assets/pages/VerifyEmail/VerifyEmail";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Registration />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/verification" element={<VerifyEmail />} />
      </Routes>
    </Router>
  );
}

export default App;
