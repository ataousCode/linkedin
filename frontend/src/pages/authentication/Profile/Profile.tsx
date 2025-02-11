import { useState } from "react";
import Box from "../../../component/Box/Box";
import Button from "../../../component/Button/Button";
import Input from "../../../component/Input/Input";
import classes from "./Profile.module.scss";
import { useNavigate } from "react-router-dom";
import { useAuthentication } from "../../../context/AuthenticationContextProvider";

function Profile() {
  const [step, setStep] = useState(0);
  const navigate = useNavigate();
  const { user, setUser } = useAuthentication();
  const [error, setError] = useState("");
  const [data, setData] = useState({
    firstName: user?.firstName || "",
    lastName: user?.lastName || "",
    company: user?.company || "",
    position: user?.position || "",
    location: user?.location || "",
  });

  const onSubmit = async () => {
    if (!data.firstName || !data.lastName) {
      setError("Please fill in your first and last name.");
      return;
    }
    if (!data.company || !data.position) {
      setError("Please fill in your latest company and position.");
      return;
    }
    if (!data.location) {
      setError("Please fill in your location.");
      return;
    }
    try {
      const response = await fetch(
        `${import.meta.env.VITE_API_URL}/api/v1/authentication/profile/${
          user?.id
        }?firstName=${data.firstName}&lastName=${data.lastName}&company=${
          data.company
        }&position=${data.position}&location=${data.location}`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );

      if (response.ok) {
        const updatedUser = await response.json();
        setUser(updatedUser);
      } else {
        const { message } = await response.json();
        throw Error(message);
      }
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError("An unknown error occurred.");
      }
    } finally {
      navigate("/");
    }
  };
  return (
    <div className={classes.root}>
      <Box>
        <h1>Only one last step</h1>
        <p>
          Tell us a bit about yourself so we can pernosalize your experience.
        </p>
        {step === 0 && (
          <div className={classes.inputs}>
            <Input
              onFocus={() => setError("")}
              required
              label="First Name"
              name="firstName"
              placeholder="Enter your first name"
              onChange={(e) =>
                setData((prev) => ({ ...prev, firstName: e.target.value }))
              }
            ></Input>
            <Input
              onFocus={() => setError("")}
              required
              label="Last Name"
              name="lastName"
              placeholder="Enter your last name"
              onChange={(e) =>
                setData((prev) => ({ ...prev, lastName: e.target.value }))
              }
            ></Input>
          </div>
        )}
        {step === 1 && (
          <div className={classes.inputs}>
            <Input
              onFocus={() => setError("")}
              label="Latest company"
              name="company"
              placeholder="Docker Inc"
              onChange={(e) =>
                setData((prev) => ({ ...prev, company: e.target.value }))
              }
            //   value={data.company}
            ></Input>
            <Input
              onFocus={() => setError("")}
              onChange={(e) =>
                setData((prev) => ({ ...prev, position: e.target.value }))
              }
            //   value={data.position}
              label="Latest position"
              name="position"
              placeholder="Software Engineer"
            ></Input>
          </div>
        )}
        {step == 2 && (
          <Input
            onFocus={() => setError("")}
            label="Location"
            name="location"
            placeholder="San Francisco, CA"
            // value={data.location}
            onChange={(e) =>
              setData((prev) => ({ ...prev, location: e.target.value }))
            }
          ></Input>
        )}
        {error && <p className={classes.error}>{error}</p>}

        <div className={classes.buttons}>
          {step > 0 && (
            <Button outline onClick={() => setStep((prev) => prev - 1)}>
              Back
            </Button>
          )}
          {step < 2 && (
            <Button
              disabled={
                (step === 0 && (!data.firstName || !data.lastName)) ||
                (step === 1 && (!data.company || !data.position))
              }
              onClick={() => setStep((prev) => prev + 1)}
            >
              Next
            </Button>
          )}
          {step === 2 && (
            <Button disabled={!data.location} onClick={onSubmit}>
              Submit
            </Button>
          )}
        </div>
      </Box>
    </div>
  );
}

export default Profile;
