import axios from "axios";
import { getTokenFromLocalStorage } from "./token";
import { updateSourceFile } from "typescript";
import { updateProduct } from "./updateProduct";


interface AdditionalSettingsRequest {
    criteria: string;
    criteriaValue: string;
}

export const addProductInThisMarket = async (image: File, description: string, name: string, price: number, id: string | undefined, addSettings: AdditionalSettingsRequest[], category: string[]) => {

    const config = {
        headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': getTokenFromLocalStorage()
        }
    };


    const formData = new FormData();



    const criteriaArray: string[] = addSettings.map((item) => item.criteria);
    const criteriaValueArray: string[] = addSettings.map((item) => item.criteriaValue);

    console.log(criteriaArray)
    console.log(criteriaValueArray)
    
    formData.append('image', image);
    formData.append('name', name);
    formData.append('price', price.toString());
    // formData.append('criteriaArray', JSON.stringify(criteriaArray));
    // formData.append('criteriaValueArray', JSON.stringify(criteriaValueArray));
    // formData.append('categories', JSON.stringify());

    if (id) formData.append('id', id);

    console.log(formData.get('addSettings'));





    try {
        if (id) {
            const response = await axios.post('http://localhost:8081/api/v1/page-market', formData, config);

            const idAddedProduct: number = response.data
            // Обработка успешного ответа
            console.log(idAddedProduct);
            const categories = category;
            console.log("Обновление продукта")
            updateProduct(idAddedProduct, description, price, categories, addSettings,  "YES");
        }
    } catch (error) {
        // Обработка ошибки
        console.error(error);
    }

};