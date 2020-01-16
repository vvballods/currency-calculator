import React, {Component} from 'react';
import axios from 'axios';
import {Button, Col, Icon, InputNumber, Row, Select} from 'antd';
import {API_BASE_URL, CURRENCY_LIST} from '../Constants'

const {Option} = Select;

class Calculator extends Component {
    state = {
        amount: '100',
        base: 'EUR',
        to: 'USD',
        result: ''
    };

    onCalculateRate = () => {
        axios.get(`${API_BASE_URL}/calculate?amount=${this.state.amount}&from=${this.state.base}&to=${this.state.to}`)
            .then(response => this.setState({result: response.data}));
    };

    render() {
        return (
            <div>
                <Row style={{height: '80px'}}>
                    <Col>
                        <label style={{padding: 8}}>Amount:</label>
                        <InputNumber id="amount" style={{width: 100}} min={0} value={this.state.amount}
                                     onChange={value => this.setState({amount: value})}>
                        </InputNumber>

                        <label style={{padding: 8}}>From:</label>
                        <Select id="base" style={{width: 100}} defaultValue="EUR"
                                onChange={value => this.setState({base: value, result: ''})}>
                            {CURRENCY_LIST.map(currency => (<Option key={currency}>{currency}</Option>))}
                        </Select>

                        <label style={{padding: 8}}>To:</label>
                        <Select id="to" style={{width: 100}} defaultValue="USD"
                                onChange={value => this.setState({to: value, result: ''})}>
                            {CURRENCY_LIST.map(currency => (<Option key={currency}>{currency}</Option>))}
                        </Select>

                        <Button style={{margin: 8}} onClick={this.onCalculateRate}><Icon type="redo"/>Convert</Button>
                    </Col>
                </Row>
                <Row style={{height: '80px'}}>
                    <Col>
                        {this.state.result && <h2>{this.state.result} {this.state.to}</h2>}
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Calculator;