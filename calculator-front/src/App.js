import React, {Component} from 'react';
import './App.css';
import {Link, Route, Switch, withRouter} from 'react-router-dom';
import {Layout, Menu} from 'antd';

import Calculator from './component/Calculator'
import Rates from "./component/Rates";

const {Header, Content, Footer} = Layout;

class App extends Component {
    render() {
        return (
            <Layout>
                <Header className="header">
                    <div className="logo"/>
                    <Menu theme="dark" mode="horizontal" defaultSelectedKeys={[this.props.location.pathname]}
                          style={{lineHeight: '64px'}}>
                        <Menu.Item key="/">
                            <span>Calculator</span>
                            <Link to="/"/>
                        </Menu.Item>
                        <Menu.Item key="/rates">
                            <span>Rates</span>
                            <Link to="/rates"/>
                        </Menu.Item>
                    </Menu>
                </Header>
                <Content style={{padding: '0 50px', margin: '24px 0px', textAlign: 'center'}}>
                    <Switch>
                        <Route path="/rates"><Rates/></Route>
                        <Route path="/"><Calculator/></Route>
                    </Switch>
                </Content>
                <Footer style={{textAlign: 'center'}}>©2020 Created by Valters Ballods</Footer>
            </Layout>
        );
    }
}

export default withRouter(App);
