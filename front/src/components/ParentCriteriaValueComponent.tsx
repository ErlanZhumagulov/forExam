import React from 'react';
import CriteriaValueComponent from './CriteriaValueComponent';

interface Props {
  criteria: string[];
  values: string[];
}

const ParentCriteriaValueComponent: React.FC<Props> = ({ criteria, values }) => {
  return (
    <div>
      {criteria.map((criterion, index) => (
        <CriteriaValueComponent
          key={index}
          criterion={criterion}
          value={values[index]}
        />
      ))}
    </div>
  );
};

export default ParentCriteriaValueComponent;