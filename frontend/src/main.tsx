import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.scss";
import { AuthenticationContextProvider } from "./context/AuthenticationContextProvider.tsx";
import HomePage from "./pages/Home/HomePage.tsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import AuthenticationLayout from "./component/AuthenticationLayout/AuthenticationLayout.tsx";
import ApplicationLayout from "./component/ApplicationLayout/ApplicationLayout.tsx";
import NotFound from "./pages/404/NotFound.tsx";
import Login from "./pages/authentication/Login/Login.tsx";
import Signup from "./pages/authentication/Signup/Signup.tsx";
import ResetPassword from "./pages/authentication/ResetPassword/ResetPassword.tsx";
import VerifyEmail from "./pages/authentication/VerifyEmail/VerifyEmail.tsx";
import Profile from "./pages/authentication/Profile/Profile.tsx";

const router = createBrowserRouter([
  {
    element: <AuthenticationContextProvider />,
    children: [
      {
        path: "/",
        element: <ApplicationLayout />,
        children: [
          {
            index: true,
            element: <HomePage />,
          },
          {
            path: "network",
            element: <div>Network</div>,
          },
          {
            path: "jobs",
            element: <div>Jobs</div>,
          },
          {
            path: "messages",
            element: <div>Messages</div>,
          },
          {
            path: "notifications",
            element: <div>Notifications</div>,
          },
          {
            path: "profile/:id",
            element: <div>Profile</div>,
          },
          {
            path: "settings",
            element: <div>Settings</div>,
          },
        ],
      },
      {
        path: "/authentication",
        element: <AuthenticationLayout />,
        children: [
          {
            path: "login",
            element: <Login />,
          },
          {
            path: "signup",
            element: <Signup />,
          },
          {
            path: "request-password-reset",
            element: <ResetPassword />,
          },
          {
            path: "verify-email",
            element: <VerifyEmail />,
          },
          {
            path: "profile/:id",
            element: <Profile />,
          },
        ],
      },
      {
        path: "*",
        element: <NotFound />,
      },
    ],
  },
]);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <RouterProvider router={router} />
  </StrictMode>
);
