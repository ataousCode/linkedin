import { FormEvent, useState } from "react";
import Box from "../../component/Box/Box";
import Button from "../../component/Button/Button";
import Input from "../../component/Input/Input";
import Layout from "../../component/Layout/Layout";
import classes from "./Login.module.scss";
import Seperator from "../../component/Seperator/Seperator";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuthentication } from "../../context/AuthenticationContextProvider.tsx";

function Login() {
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();
  const { login } = useAuthentication();

  const doLogin = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const email = e.currentTarget.email.value;
    const password = e.currentTarget.password.value;

    try {
      await login(email, password);
      const destination = location.state?.from || "/";
      navigate(destination);
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      } else {
        setErrorMessage("An unknown error occurred. Please try again.");
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Layout>
      <Box>
        <h1>Sign In</h1>
        <p>Stay updated on your professional world.</p>
        <form onSubmit={doLogin}>
          <Input
            label="email"
            type="email"
            id="email"
            placeholder="email address"
          />
          <Input
            label="password"
            type="password"
            id="password"
            placeholder="password"
          />
          {errorMessage && <p className={classes.error}>{errorMessage}</p>}
          <Button type="submit" disabled={isLoading}>
            {isLoading ? "..." : "Sign in"}
          </Button>
          <Link to="/request-password-reset">Forgot password?</Link>
        </form>
        <Seperator>Or</Seperator>
        <div className={classes.register}>
          New to LinkedIn? <Link to="/signup">Join now</Link>
        </div>
      </Box>
    </Layout>
  );
}

export default Login;
