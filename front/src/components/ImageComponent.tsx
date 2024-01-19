import axios from 'axios';
import React, { useEffect, useState } from 'react';


const getImage = async (imageId: string): Promise<string> => {
    try {
      const response = await axios.get(`http://localhost:8081/api/v1/page-product`, { responseType: 'arraybuffer', params: {
        imageId: imageId
      } });
      const base64Image = btoa(
        new Uint8Array(response.data).reduce(
          (data, byte) => data + String.fromCharCode(byte),
          '',
        ),
      );
      return `data:${response.headers['content-type']};base64,${base64Image}`;
    } catch (error) {
      console.error('Error retrieving image:', error);
      throw error;
    }
  };
  
const ImageComponent: React.FC<{ imageId: string }> = ({ imageId }) => {
  const [imageSrc, setImageSrc] = useState<string | null>(null);

  useEffect(() => {
    getImage(imageId)
      .then((src) => setImageSrc(src))
      .catch((error) => console.error('Error:', error));
  }, [imageId]);

  if (!imageSrc) {
    return <div>Loading...</div>;
  }

  return <img src={imageSrc}  style={{ width: '200px', height: 'auto' }} alt="Image" />;
};

export default ImageComponent;
