import Button from "../../../../component/Button/Button";
import classes from "./RightSideBar.module.scss";

function RightSideBar() {
  return (
    <div className={classes.root}>
      <h3>Add to your feed</h3>
      <div className={classes.items}>
        <div className={classes.item}>
          <img
            src="https://i.pravatar.cc/300"
            alt=""
            className={classes.avatar}
          />
          <div className={classes.content}>
            <div className={classes.name}>Anis Done</div>
            <div className={classes.title}>Flutter Developer</div>
           <Button size="medium" outline className={classes.button}>+ Follow</Button>
          </div>
        </div>

        <div className={classes.item}>
          <img
            src="https://i.pravatar.cc/500"
            alt=""
            className={classes.avatar}
          />
          <div className={classes.content}>
            <div className={classes.name}>Saku Md</div>
            <div className={classes.title}>Python Developer</div>
           <Button size="medium" outline className={classes.button}>+ Follow</Button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RightSideBar;
