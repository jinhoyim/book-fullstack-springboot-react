import axios from "axios";
import { beforeAll } from "vitest";

beforeAll(async () => {
  await axios
    .post(import.meta.env.VITE_API_URL + "/login", {
      username: "user",
      password: "user",
    })
    .then((res) => {
      const jwtToken = res.headers.authorization;
      sessionStorage.setItem("jwt", jwtToken);
    });
});
