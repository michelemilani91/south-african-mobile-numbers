import React from 'react';
import styles from './index.css';

const BasicLayout: React.FC = props => {
  return (
    <div className={styles.normal}>
      <h1 className={styles.title}>South African Mobile Numbers test for <a href="https://www.interlogica.it/">Interlogica srl</a></h1>
      {props.children}
    </div>
  );
};

export default BasicLayout;
