import axios from "axios";
import { getTokenFromLocalStorage } from "./token";


interface Image {
    bytes: Uint8Array

}
interface getImageRequest {
    idProduct: number;

}
const body: getImageRequest = {
    idProduct: 1,

}

export const getAllImagesForOneProduct = async (id: number): Promise<ArrayBufferLike> => {
    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getTokenFromLocalStorage()
        }
    };


    const response = await axios.get('http://localhost:8081/api/v1/page-product', config);

    const bytes: ArrayBufferLike = response.data[0];
  

    console.log("response")
    console.log(bytes);
   


    return bytes;


};