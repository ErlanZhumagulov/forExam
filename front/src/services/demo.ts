import axios, { AxiosInstance } from 'axios';
import { getTokenFromLocalStorage } from './token';

interface AddMarketRequest {
  marketName: string;
  marketDescription: string;
  address: string;
  x: number;
  y: number;
}



export const getToken = async () => {
  const token: string = getTokenFromLocalStorage(); // Получите JWT-токен после аутентификации
  console.log("Ниже то")
  console.log(token);

  const requestData = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getTokenFromLocalStorage()
    },

  };

  axios.get('http://localhost:8081/api/v1/page-seller', requestData)
    .then(response => {
      // Обработка успешного ответа
      console.log(response.data);
    })
    .catch(error => {
      // Обработка ошибки
      console.error(error);
    });
};

export const addMarket = async (nameMarket: string, descriptionMarket: string, addressMarket: string, xCoordinate: number, yCoordinate: number) => {

  const config = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getTokenFromLocalStorage()
    }
  };

  const body: AddMarketRequest = {
    marketName: nameMarket,

    marketDescription: descriptionMarket,

    address: addressMarket,

    x: xCoordinate,

    y: yCoordinate
  }

  try {
    const response = await axios.post('http://localhost:8081/api/v1/page-seller/add_market', body, config);
    // Обработка успешного ответа
    console.log(response.data);
  } catch (error) {
    // Обработка ошибки
    console.error(error);
  }

};

interface ShopItem {
  id: number;
  marketName: string;
  marketDescription: string;
  address: string;
  x: number;
  y: number;
  seller: any;
  products: any[];

}

export const getMarkets = async (): Promise<ShopItem[]> => {

  const config = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getTokenFromLocalStorage()
    }
  };


  const response = await axios.get('http://localhost:8081/api/v1/page-seller/get_markets', config);
  console.log(response);
  return response.data;


};




interface Product {
  id: number;
  nameProduct: string;
  price: string;
  description: string;
  availability: string;
  idImages: number[];
  additionalSettingsCriteriaList: string[];
  additionalSettingsValueList: string[];
  categoriesList: string[];
}



export const getAllProducts = async (id: any): Promise<Product[]> => {

  console.log("Id = ", id)
  const config = {
    headers: {
      'Content-Type': 'application/json',
      'Authorization': getTokenFromLocalStorage()
    }

  };


  // const formData = new FormData();
  // formData.append('id', id);

  const response = await axios.get('http://localhost:8081/api/v1/page-market/get_for_one_market', {
    params: {
      id: id
    }
  });
  console.log(response);
  return response.data;


};

