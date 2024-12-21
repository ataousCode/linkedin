import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.scss";
import { AuthenticationContextProvider } from "./context/AuthenticationContextProvider.tsx";
import HomePage from "./pages/Home/HomePage.tsx";
import Login from "./pages/Login/Login.tsx";
import Signup from "./pages/Signup/Signup.tsx";
import ResetPassword from "./pages/ResetPassword/ResetPassword.tsx";
import VerifyEmail from "./pages/VerifyEmail/VerifyEmail.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

const router = createBrowserRouter([
  {
    element: <AuthenticationContextProvider />,
    children: [
      {
        path: "/",
        element: <HomePage />,
      },
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/signup",
        element: <Signup />,
      },
      {
        path: "/request-password-reset",
        element: <ResetPassword />,
      },
      {
        path: "/verify-email",
        element: <VerifyEmail />,
      },
    ],
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
