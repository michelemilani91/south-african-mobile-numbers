import React from 'react';
import {Button, Space, Card} from 'antd';
import { history } from 'umi';

export default function() {
  return (
    <Card title="Choose one">
      <Space>
        <Button onClick={() => history.push("/single")}>Single number check</Button>
        <Button onClick={() => history.push("/list")}>CSV file check</Button>
      </Space>
    </Card>
  );
}
