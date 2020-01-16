import React, {Component} from 'react';
import axios from 'axios';
import {Button, Col, Icon, InputNumber, Row, Select, Table} from 'antd';
import {API_BASE_URL, CURRENCY_LIST} from '../Constants'

const {Option} = Select;

class Rates extends Component {
    state = {
        base: 'EUR',
        to: 'USD',
        fee: '0.01',
        rates: []
    };

    columns = [
        {
            title: 'From',
            dataIndex: 'base',
            key: 'base'
        },
        {
            title: 'To',
            dataIndex: 'to',
            key: 'to'
        },
        {
            title: 'Fee',
            dataIndex: 'fee',
            key: 'fee'
        },
        {
            title: 'Action',
            dataIndex: 'id',
            key: 'action',
            render: (id) => (
                <span>
                    <Button type="danger" shape="circle" icon="delete" onClick={() => this.onRemoveFee(id)}/>
                </span>
            )
        }
    ];

    onInit = () => {
        axios.get(`${API_BASE_URL}/rates`)
            .then(response => this.setState({rates: response.data}));
    };

    onApplyFee = () => {
        axios.post(`${API_BASE_URL}/add`, {
            base: this.state.base,
            to: this.state.to,
            fee: this.state.fee
        }).then(() => this.onInit());
    };

    onRemoveFee = (id) => {
        axios.delete(`${API_BASE_URL}/remove/${id}`)
            .then(() => this.onInit());
    };

    componentDidMount() {
        this.onInit();
    }

    render() {
        return (
            <div>
                <Row style={{height: '80px'}}>
                    <Col>
                        <label style={{padding: 8}}>From:</label>
                        <Select id="base" style={{width: 100}} defaultValue="EUR"
                                onChange={value => this.setState({base: value})}>
                            {CURRENCY_LIST.map(currency => (<Option key={currency}>{currency}</Option>))}
                        </Select>

                        <label style={{padding: 8}}>To:</label>
                        <Select id="to" style={{width: 100}} defaultValue="USD"
                                onChange={value => this.setState({to: value})}>
                            {CURRENCY_LIST.map(currency => (<Option key={currency}>{currency}</Option>))}
                        </Select>

                        <label style={{padding: 8}}>Fee:</label>
                        <InputNumber id="amount" style={{width: 100}} min={0} value={this.state.fee}
                                     onChange={value => this.setState({fee: value})}>
                        </InputNumber>

                        <Button style={{margin: 8}} onClick={this.onApplyFee}><Icon type="redo"/>Apply</Button>
                    </Col>
                </Row>
                <Row style={{height: '80px'}}>
                    <Col>
                        <Table rowKey="id" columns={this.columns} dataSource={this.state.rates}/>
                    </Col>
                </Row>
            </div>
        );
    }
}

export default Rates;