import axios from "axios";
import { getTokenFromLocalStorage } from "./token";
import { updateSourceFile } from "typescript";


interface AdditionalSettingsRequest {
    criteria: string;
    criteriaValue: string;
}

export const updateProduct = async (idAddedProduct: number, description: string, price: number, categories: string[], additionalSettingsRequests: AdditionalSettingsRequest[], availability: string) => {

    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getTokenFromLocalStorage()
        }
    };
    const body = {
        idAddedProduct: idAddedProduct,
        price: price,
        description: description,
        categories: categories,
        additionalSettingsRequests: additionalSettingsRequests,
        availability: availability
      };
    
    
      try {
        const response = await axios.post("http://localhost:8081/api/v1/page-market/update-product", body, config);
        // Обработка успешного ответа от сервера
        console.log(response.data);
      } catch (error) {
        // Обработка ошибок
        console.error(error);
      }
    };