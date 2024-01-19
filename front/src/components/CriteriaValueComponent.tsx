import React from 'react';

interface Props {
  criterion: string;
  value: string;
}

const CriteriaValueComponent: React.FC<Props> = ({ criterion, value }) => {
  return (
    <div>
      <span>{criterion}</span>: <span>{value}</span>
    </div>
  );
};

export default CriteriaValueComponent;