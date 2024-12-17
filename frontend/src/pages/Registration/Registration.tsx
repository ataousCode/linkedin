import { useState } from "react";
import Box from "../../component/Box/Box";
import Input from "../../component/Input/Input";
import Layout from "../../component/Layout/Layout";
import Button from "../../component/Button/Button";
import Seperator from "../../component/Seperator/Seperator";
import { Link } from "react-router-dom";
import classes from "./Registration.module.scss";

function Registration() {
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  return (
    <Layout>
      <Box>
        <h1>Sign Up to continue</h1>
        <p>Make the most of your professional life.</p>
        <form>
          <Input type="email" id="email" label="email" />
          <Input type="password" id="password" label="password" />
        </form>
        <p className={classes.disclaimer}>
          By clicking Agree & Join or Continue, you agree to LinkedIn's{" "}
          <a href="">User Agreement</a>, <a href="">Privacy Policy</a>, and{" "}
          <a href="">Cookie Policy</a>.
        </p>
        <Button disabled={isLoading} type="submit">
          Continue
        </Button>
        <Seperator>Or</Seperator>
        <div className={classes.register}>
          Already on LinkedIn? <Link to="/login">Sign in</Link>
        </div>
      </Box>
    </Layout>
  );
}

export default Registration;
