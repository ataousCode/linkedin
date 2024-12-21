import { FormEvent, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Box from "../../component/Box/Box.tsx";
import Input from "../../component/Input/Input.tsx";
import Layout from "../../component/Layout/Layout.tsx";
import Button from "../../component/Button/Button.tsx";
import Seperator from "../../component/Seperator/Seperator.tsx";
import { useAuthentication } from "../../context/AuthenticationContextProvider.tsx";
import classes from "./Signup.module.scss";

function Registration() {
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const { signup } = useAuthentication();
  const navigate = useNavigate();

  const doSignup = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setIsLoading(true);
    const email = e.currentTarget.email.value;
    const password = e.currentTarget.password.value;
    try {
      await signup(email, password);
      navigate("/");
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      } else {
        setErrorMessage("An unknown error occurred. Please try again");
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Layout className={classes.root}>
      <Box>
        <h1>Sign Up to continue</h1>
        <p>Make the most of your professional life.</p>
        <form onSubmit={doSignup}>
          <Input
            type="email"
            id="email"
            label="Email"
            placeholder="email address"
            onFocus={() => setErrorMessage("")}
          />
          <Input
            type="password"
            id="password"
            label="Password"
            placeholder="Password"
            onFocus={() => setErrorMessage("")}
          />
          <p className={classes.disclaimer}>
            By clicking Agree & Join or Continue, you agree to LinkedIn's{" "}
            <a href="">User Agreement</a>, <a href="">Privacy Policy</a>, and{" "}
            <a href="">Cookie Policy</a>.
          </p>
          {errorMessage && <p className={classes.error}>{errorMessage}</p>}
          <Button disabled={isLoading} type="submit">
            Continue
          </Button>
        </form>
        <Seperator>Or</Seperator>
        <div className={classes.register}>
          Already on LinkedIn? <Link to="/login">Sign in</Link>
        </div>
      </Box>
    </Layout>
  );
}

export default Registration;
