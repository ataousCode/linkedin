import React from "react";
import "./SignUp.css";

const SignIn = () => {
  return (
    <div className="signin-container">
      <h2>Welcome Back</h2>
      <form>
        <input type="email" placeholder="Email" required />
        <input type="password" placeholder="Password" required />
        <button type="submit">Sign In</button>
      </form>
    </div>
  );
};

export default SignIn;
