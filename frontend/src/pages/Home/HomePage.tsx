import { useNavigate } from "react-router-dom";
import { useAuthentication } from "../../context/AuthenticationContextProvider";
import classes from "./HomePage.module.scss";
import Button from "../../component/Button/Button";
import { useEffect, useState } from "react";
import Post, { IPost } from "./Components/Post/Post";
import RightSideBar from "./Components/RightSideBar/RightSideBar";
import LeftSidebar from "./Components/LeftSidebar/LeftSidebar";
import Modal from "./Components/Modal/Modal";

function HomePage() {
  const navigate = useNavigate();
  const { user } = useAuthentication();
  const [feedContent, setFeedContent] = useState<"all" | "connexions">(
    "connexions"
  );
  const [showPostingModal, setShowPostingModal] = useState(false);
  const [posts, setPosts] = useState<IPost[]>([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await fetch(
          import.meta.env.VITE_API_URL +
            "/api/v1/posts" +
            (feedContent === "connexions" ? "" : "/all"),
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );
        if (!response.ok) {
          const { message } = await response.json();
          throw new Error(message);
        }
        const data = await response.json();
        setPosts(data);
      } catch (error) {
        if (error instanceof Error) {
          setError(error.message);
        } else {
          setError("An error occurred. please try again later.");
        }
      }
    };
    fetchPosts();
  }, [feedContent]);

  const handlePost = async (content: string, picture: string) => {
    const response = await fetch(
      `${import.meta.env.VITE_API_URL}/api/v1/posts/create`,
      {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ content, picture }),
      }
    );
    if (!response.ok) {
      const { message } = await response.json();
      throw new Error(message);
    }
  };

  return (
    <div className={classes.root}>
      <div className={classes.left}>
        <LeftSidebar />
      </div>
      <div className={classes.center}>
        <div className={classes.posting}>
          <button
            onClick={() => {
              navigate(`/profile/${user?.id}`);
            }}
          >
            <img
              className={`${classes.top} ${classes.avatar}`}
              src={user?.profilePicture || "/avatar.svg"}
              alt=""
            />
          </button>
          <Button outline onClick={() => setShowPostingModal(true)}>
            Start a post
          </Button>

          <Modal
            title="Creating a post"
            onSubmit={handlePost}
            showModal={showPostingModal}
            setShowModal={setShowPostingModal}
          />
        </div>
      </div>
      <div className={classes.right}>
        <RightSideBar />
      </div>

      {error && <div className={classes.error}>{error}</div>}

      <div className={classes.header}>
        <button
          className={feedContent === "all" ? classes.active : ""}
          onClick={() => setFeedContent("all")}
        >
          All
        </button>
        <button
          className={feedContent === "connexions" ? classes.active : ""}
          onClick={() => setFeedContent("connexions")}
        >
          Feed
        </button>
      </div>

      <div className={classes.feed}>
        {posts.map((post) => (
          <Post key={post.id} post={post} setPosts={setPosts} />
        ))}
      </div>
    </div>
  );
}

export default HomePage;
