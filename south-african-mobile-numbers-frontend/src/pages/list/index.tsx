import React, {useRef, useState} from 'react';
import ProTable, {ActionType, ProColumns} from '@ant-design/pro-table';
import {Button, message, Upload, UploadProps} from 'antd';
import {InboxOutlined} from '@ant-design/icons';
import request from 'umi-request';

import {Elaboration, ElaborationStatus, Page} from './data.d';
import {Link} from "umi";

export default function() {
  const [loading, setLoading] = useState<boolean>(false);
  const columns: ProColumns<Elaboration>[] = [
    {
      dataIndex: 'id',
      width: 48,
    },
    {
      title: 'Name',
      dataIndex: 'name',
      render: (text, record, _, action) =>
        record.status !== ElaborationStatus.PROCESSED ? text :
          <Link to={`/list/${record.id}`}>{text}</Link>,
    },
    {
      title: 'Status',
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: {
        all: { text: 'All', status: 'Default' },
        open: {
          text: ElaborationStatus.UNPROCESSED,
          status: 'Error',
        },
        closed: {
          text: ElaborationStatus.PROCESSED,
          status: 'Success',
        },
        processing: {
          text: ElaborationStatus.PROCESSING,
          status: 'Processing',
        },
      },
    },
    {
      title: 'Actions',
      valueType: 'option',
      render: (text, record, _, action) =>
        <Button
          type="link"
          disabled={record.status !== ElaborationStatus.UNPROCESSED}
          onClick={async () => {
            setLoading(true);
            await request<void>(`/api/process/${record.id}`, { method: "POST" });
            setLoading(false);
            action.reload();
          }}
        >
          Process
        </Button>
    },
  ];
  const actionRef = useRef<ActionType>();
  const uploadProps: UploadProps = {
    name: 'file',
    action: '/api/upload',
    accept: '.csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel',
    multiple: false,
    showUploadList: false,
    onChange(info) {
      if (info.file.status !== 'uploading') {
        console.log(info.file, info.fileList);
      }
      if (info.file.status === 'done') {
        actionRef.current?.reload();
        message.success(`${info.file.name} file uploaded successfully`);
      } else if (info.file.status === 'error') {
        message.error(`${info.file.name} file upload failed.`);
      }
    },
  };
  return (
  <>
    <Upload.Dragger {...uploadProps}>
      <p className="ant-upload-drag-icon">
        <InboxOutlined />
      </p>
      <p className="ant-upload-text">Click or drag file to this area to upload</p>
      <p className="ant-upload-hint">Support for a single upload.</p>
    </Upload.Dragger>
    <ProTable<Elaboration>
      loading={loading}
      search={false}
      columns={columns}
      actionRef={actionRef}
      request={async (params, sort, filter) => {
        const msg = await request<Page<Elaboration>>(
          '/api/list', {
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
