import axios, { AxiosInstance } from 'axios';
import { getTokenFromLocalStorage } from './token';




export const saveImage = async (file: File) => {
  
    const formData = new FormData();
    formData.append('file', file);
  
    const config = {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': getTokenFromLocalStorage()
      }
    };
  
    try {
      const response = await axios.post('http://localhost:8081/api/v1/page-seller', formData, config);
      // Обработка успешного ответа
      console.log(response.data);
    } catch (error) {
      // Обработка ошибки
      console.error(error);
    }
    
    
};
