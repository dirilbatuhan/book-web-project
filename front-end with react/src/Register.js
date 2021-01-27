import React from "react";
import "./App.css";
import { Form, Button, Container, Grid, Divider } from "semantic-ui-react";
import { Link, withRouter } from "react-router-dom";
import fetch from "isomorphic-unfetch";
import { toast } from "react-toastify";

class Register extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      passwordRepeat: "",
      usernameError: null,
      passwordError: null,
      passwordRepeatError: null,
      counter: 0,
    };
  }

  handleChange = (e) => {
    const { currentTarget } = e;
    const { value, name } = currentTarget;

    this.setState({ [name]: value });
  };

  handleSubmit = (e) => {
    e.preventDefault();
    this.setState({ counter: this.state.counter + 1 });
    const { username, password, passwordRepeat } = this.state;
    if (username.length < 5 || username.length > 255) {
      this.setState({
        usernameError: "Lütfen Kullanıcı adını kontrol ediniz",
      });
      return;
    }
    if (password.length < 3 || password.length > 255) {
      this.setState({
        passwordError: "Lütfen şifrenizi kontrol ediniz",
      });
      return;
    }
    if (password !== passwordRepeat) {
      this.setState({
        passwordError: "Lütfen şifrelerinizi kontrol ediniz",
        passwordRepeatError: "Lütfen şifrelerinizi kontrol ediniz",
      });
      return;
    }

    fetch("http://localhost:8080/api/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    })
      .then((r) => {
        if (r.ok) {
          return r;
        }
        if (r.status === 401 || r.status === 403 || r.status === 500) {
          return Promise.reject(new Error("Bir hata oluştu"));
        }
      })
      .then((r) => r.json())
      .then((response) => {
        toast.success("Kayıt başarılı! Giriş sayfasına yönlendiriliyorsunuz");
        setTimeout(() => {
          this.props.history.push("/login");
        }, 1500);
      })
      .catch((e) => {
        toast.error(e.message);
      });
  };

  render = () => {
    const {
      counter,
      usernameError,
      passwordError,
      passwordRepeatError,
    } = this.state;
    return (
      <div className="App">
        <Container>
          <Grid>
            <Grid.Row columns="equal" centered>
              <Grid.Column width={8}>
                <p>Şu anki counter değeri : {counter}</p>
                <Form
                  onSubmit={this.handleSubmit}
                  onReset={(e) => {
                    e.preventDefault();
                    this.setState({
                      counter: 0,
                      username: "",
                      password: "",
                      passwordRepeat: "",
                    });
                  }}
                >
                  <Form.Field>
                    <label>Kullanıcı Adı</label>
                    <Form.Input
                      type="email"
                      name="username"
                      required
                      error={usernameError}
                      value={this.state.username}
                      onChange={this.handleChange}
                    />
                  </Form.Field>

                  <Form.Field>
                    <label>Şifre</label>
                    <Form.Input
                      type="password"
                      name="password"
                      required
                      error={passwordError}
                      value={this.state.password}
                      onChange={this.handleChange}
                    />
                  </Form.Field>

                  <Form.Field>
                    <label>Şifre Tekrarı</label>
                    <Form.Input
                      type="password"
                      name="passwordRepeat"
                      required
                      error={passwordRepeatError}
                      value={this.state.passwordRepeat}
                      onChange={this.handleChange}
                    />
                  </Form.Field>
                  <Button.Group fluid>
                    <Button color="teal" type="submit">
                      Kaydet
                    </Button>
                    <Button type="reset">Sıfırla</Button>
                  </Button.Group>
                  <Divider />
                  <Link to="/login">Hesabınız var mı? Giriş yapın</Link>
                </Form>
              </Grid.Column>
            </Grid.Row>
          </Grid>
        </Container>
      </div>
    );
  };
}

export default withRouter(Register);
