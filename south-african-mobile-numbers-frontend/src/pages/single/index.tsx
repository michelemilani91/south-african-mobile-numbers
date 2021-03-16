import React, {useState} from "react";
import {Button, Card, Descriptions, Form, Input, message} from "antd";
import request from "umi-request";

import {MobileNumberValidationResult} from './data.d';

export default function () {
  const [response, setResponse] = useState<MobileNumberValidationResult|undefined>();
  const handleSubmit = async (values: any) => {
    const resp = await request<MobileNumberValidationResult>('/api/single', {
      method: 'POST',
      body: values.mobileNumber,
    });
    setResponse(resp);
    if (resp.valid)
      message.success("Valid mobile phone number");
    else
      message.error("Not valid mobile phone number");
  };
  return (
    <>
      <Card>
        <Form layout={"inline"} onFinish={handleSubmit}>
          <Form.Item name="mobileNumber" label="Mobile number">
            <Input/>
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">
              Check validity
            </Button>
          </Form.Item>
          <Form.Item>
            <Button onClick={() => setResponse(undefined)}>
              Clean
            </Button>
          </Form.Item>
        </Form>
      </Card>
      {response && response.valid &&
        <Card>
          Valid phone number: {response.formattedNumber}
        </Card>
      }
      {response && !response.valid &&
        <Card>
          Not valid phone number: {response.errorMessage}
        </Card>
      }
    </>
  );
}
