import React, { useState } from "react";
import "./App.css";
import { Form, Button, Container, Grid, Divider } from "semantic-ui-react";
import { useHistory } from "react-router-dom";
import fetch from "isomorphic-unfetch";
import { toast } from "react-toastify";

const Login = ({ showRegisterLink }) => {
  const history = useHistory();

  const [usernamePassword, setUsernamePassword] = useState({
    username: "",
    password: "",
  });

  const [usernamePasswordError, setUsernamePasswordError] = useState({
    username: null,
    password: null,
  });

  const handleChange = (e) => {
    const { currentTarget } = e;
    const { value, name } = currentTarget;

    setUsernamePassword({ ...usernamePassword, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const { username, password } = usernamePassword;
    if (username.length < 5 || username.length > 255) {
      setUsernamePasswordError({
        ...usernamePasswordError,
        username: "Lütfen Kullanıcı adını kontrol ediniz",
      });
      return;
    }
    if (password.length < 3 || password.length > 255) {
      setUsernamePasswordError({
        ...usernamePasswordError,
        passwordError: "Lütfen şifrenizi kontrol ediniz",
      });
      return;
    }

    const formData = new URLSearchParams();
    formData.append("username", username);
    formData.append("password", password);
    fetch("http://localhost:8080/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: formData,
      credentials: "include",
    })
      .then((r) => {
        if (r.ok) {
          return r;
        }
        if (r.status === 401 || r.status === 403 || r.status === 500) {
          return Promise.reject(new Error("Bir hata oluştu"));
        }
      })
      .then((response) => {
        toast.success("Giriş işlemi başarılı! Yönlendiriliyorsunuz");
        setTimeout(() => {
          history.push("/dashboard");
        }, 1500);
      })
      .catch((e) => {
        toast.error(e.message);
      });
  };

  return (
    <div className="App">
      <Container>
        <Grid>
          <Grid.Row columns="equal" centered>
            <Grid.Column width={8}>
              <Form
                onSubmit={handleSubmit}
                onReset={(e) => {
                  e.preventDefault();
                  setUsernamePassword({ username: "", password: "" });
                }}
              >
                <Form.Field>
                  <label>Kullanıcı Adı</label>
                  <Form.Input
                    type="email"
                    name="username"
                    required
                    error={usernamePasswordError.username}
                    value={usernamePassword.username}
                    onChange={handleChange}
                  />
                </Form.Field>

                <Form.Field>
                  <label>Şifre</label>
                  <Form.Input
                    type="password"
                    name="password"
                    required
                    error={usernamePasswordError.password}
                    value={usernamePassword.password}
                    onChange={handleChange}
                  />
                </Form.Field>
                <Button.Group fluid>
                  <Button color="teal" type="submit">
                    Giriş
                  </Button>

                  <Button type="reset">Sıfırla</Button>
                </Button.Group>
                <Divider />
                {showRegisterLink && (
                  <Button
                    fluid
                    type="button"
                    color="primary"
                    onClick={() => {
                      history.push("/");
                    }}
                  >
                    Hesabınız yok mu? Kayıt olun
                  </Button>
                )}
              </Form>
            </Grid.Column>
          </Grid.Row>
        </Grid>
      </Container>
    </div>
  );
};
export default Login;
