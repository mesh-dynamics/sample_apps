import React from "react";
import { connect } from "react-redux";
import { InputGroup, FormControl, Button } from "react-bootstrap";
import { authActions } from "../../actions";

import "./LoginPage.css";

const LoginPage = (props) => {
  const { authentication, login } = props;

  return (
    <div className="login-root">
      <div className="login-container">
        <div className="login-input-section">
          <span className="login-header">Welcome to Moviebook App!</span>
          <div className="login-input-wrapper">
            <InputGroup className="mb-3">
              <InputGroup.Prepend>
                <InputGroup.Text id="inputGroup-sizing-sm">
                  Username
                </InputGroup.Text>
              </InputGroup.Prepend>
              <FormControl
                placeholder="Email or User Id"
                aria-label="Email or User Id"
                aria-describedby="basic-addon2"
              />
            </InputGroup>
            <InputGroup className="mb-3">
              <InputGroup.Prepend>
                <InputGroup.Text
                  className="login-password-text"
                  id="inputGroup-sizing-sm"
                >
                  Password
                </InputGroup.Text>
              </InputGroup.Prepend>
              <FormControl
                type="password"
                placeholder="Password"
                aria-label="Password"
                aria-describedby="basic-addon2"
              />
            </InputGroup>
            <Button onClick={() => login("test", "test")} variant="primary">
              Login
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = (state) => ({
  authentication: state.authentication,
});

const mapDispatchToProps = (dispatch) => ({
  login: (username, password) =>
    dispatch(authActions.login(username, password)),
});

export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);
