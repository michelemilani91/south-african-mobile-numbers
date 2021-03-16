import React from 'react';
import ProTable, {ProColumns} from '@ant-design/pro-table';
import request from 'umi-request';
import {IRouteComponentProps, Link} from 'umi';

import {MobileNumber, Page} from './data.d';
import {Tag} from "antd";
import {CheckCircleOutlined, CloseCircleOutlined} from "@ant-design/icons";

const columns: ProColumns<MobileNumber>[] = [
  {
    title: 'CSV ID',
    dataIndex: 'originalId',
  },
  {
    title: 'CSV mobile number',
    dataIndex: 'originalMobileNumber',
  },
  {
    title: 'Corrected mobile number',
    dataIndex: 'correctedMobileNumber',
  },
  {
    title: 'Validity',
    dataIndex: 'valid',
    render: (text, record, _, action) =>
      record.valid ?
        <Tag icon={<CheckCircleOutlined />} color="success">
          Valid
        </Tag> :
        <Tag icon={<CloseCircleOutlined />} color="error">
          Not valid
        </Tag>
  },
  {
    title: 'Error',
    dataIndex: 'errorMessage',
  },
];

export default function(props: IRouteComponentProps<{id: string}>) {
  return (
    <>
      <ProTable<MobileNumber>
        search={false}
        columns={columns}
        request={async (params, sort, filter) => {
          const msg = await request<Page<MobileNumber>>(
            `/api/list/${props.match.params.id}`, {
              params:{
                page: (params.current || 1) - 1,
                size: params.pageSize,
              },
            });
          return {
            data: msg.content,
            success: true,
            total: msg.totalElements,
          };
        }}
        rowKey="id"
        pagination={{
          pageSize: 10,
        }}
      />
    </>
  );
};
