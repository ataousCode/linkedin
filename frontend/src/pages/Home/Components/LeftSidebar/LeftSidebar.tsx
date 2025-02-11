import { useAuthentication } from "../../../../context/AuthenticationContextProvider";
import classes from "./LeftSidebar.module.scss";

function LeftSidebar() {
  const { user } = useAuthentication();
  return (
    <div className={classes.root}>
      <div className={classes.cover}>
        <img src="https://i.pravatar.cc/500" alt="Cover" />
      </div>
      <div className={classes.avatar}>
        <img src={user?.profilePicture || "/avatar.svg"} alt="" />
      </div>
      <div className={classes.name}>
        {user?.firstName + " " + user?.lastName}
      </div>
      <div className={classes.title}>
        {user?.position + " " + user?.company}
      </div>
      <div className={classes.info}>
        <div className={classes.item}>
          <div className={classes.label}>Profile viewers</div>
          <div className={classes.value}>1,234</div>
        </div>
        <div className={classes.item}>
          <div className={classes.label}>Connexions</div>
          <div className={classes.value}>4,450</div>
        </div>
      </div>
    </div>
  );
}

export default LeftSidebar;
