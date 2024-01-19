import React, { useState } from 'react';

interface AdditionalSettingsRequest {
  criteria: string;
  criteriaValue: string;
}

const AddSettings: React.FC = () => {
  const [additionalSettings, setAdditionalSettings] = useState<AdditionalSettingsRequest[]>([]);

  const handleInputChange = (index: number, field: string, value: string) => {
    const updatedSettings = [...additionalSettings];
    updatedSettings[index] = {
      ...updatedSettings[index],
      [field]: value
    };
    setAdditionalSettings(updatedSettings);
  };

  return (
    <div>
      {additionalSettings.map((settings, index) => (
        <div key={index}>
          <input
            type="text"
            value={settings.criteria}
            onChange={(e) => handleInputChange(index, 'criteria', e.target.value)}
          />
          <input
            type="text"
            value={settings.criteriaValue}
            onChange={(e) => handleInputChange(index, 'criteriaValue', e.target.value)}
          />
        </div>
      ))}
    </div>
  );
};

export default AddSettings;
