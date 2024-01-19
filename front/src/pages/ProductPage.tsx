import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import ParentCriteriaValueComponent from '../components/ParentCriteriaValueComponent';
import ImageComponent from '../components/ImageComponent';
import axios from 'axios';
import { YMaps, Placemark, Map } from '@pbe/react-yandex-maps';

interface ProductItemProps {
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

interface GeoInfo {
    x: number;
    
    y: number;
    
    address: string;

    marketName: string

}




const ProductPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [product, setProduct] = useState<ProductItemProps | null>(null);
    const [placemarkGeometry, setPlacemarkGeometry] = useState([55.753215, 37.622504]);
    const [center, setCenterCoordinates] = useState([55.753215, 37.622504]);
    const [address, setAddress] = useState("");
    const [nameMarket, setNameMarket] = useState("");

    const mapState = {
        center, // Координаты центра карты
        zoom: 10 // Масштаб карты
    };



    useEffect(() => {

        const fetchProduct = async () => {
            try {
                const response = await axios.get('http://localhost:8081/api/v1/page-product/get_product_by_id', {
                    params: {
                        id: id
                    },
                });
                const responseMap = await axios.get('http://localhost:8081/api/v1/page-product/get_geo', {
                    params: {
                        id: id
                    },
                });


                console.log(responseMap)
             //   console.log(responseMap.data.nameMarket)
                setPlacemarkGeometry([responseMap.data.x, responseMap.data.y]);
                setCenterCoordinates([responseMap.data.x, responseMap.data.y]);



                setAddress(responseMap.data.address);
                setNameMarket(responseMap.data.marketName);

                console.log(placemarkGeometry)
                setProduct(response.data);
            } catch (error) {
                console.error('Error fetching product:', error);
            }
        };
        // Здесь можно вызвать функцию, которая будет получать данные о продукте с сервера по его id
        // Например, getProductById(id).then((data) => setProduct(data));
        fetchProduct();
    }, [id]);

    if (!product) {
        return <div>Загрузка...</div>;
    }

    return (
        <div style={{
            border: '1px solid black',
            padding: '10px',
            margin: '20px'
          }}>
            <h2>Название продукта: {product.nameProduct}</h2>
            <h3>Цена: {product.price}</h3>
            <h3>Описание: {product.description}</h3>
            <h3>Наличие: {product.availability}</h3>
            <h3>Название магазина: {nameMarket}</h3>
            <h3>Адрес магазина: {address}</h3>

            <ParentCriteriaValueComponent
                criteria={product.additionalSettingsCriteriaList}
                values={product.additionalSettingsValueList}
            />
            <div>
                {product.categoriesList.map((item: any, index: any) => (
                    <div key={index}>{item}</div>
                ))}
                <div>
                    <ImageComponent imageId={product.idImages[0].toString()} />
                </div>
                <div>
                    <YMaps>
                        <Map
                            state={mapState}
                            width="100%"
                            height="400px"
                        >
                            <Placemark geometry={placemarkGeometry} /> {/* Местоположение метки */}
                        </Map>
                    </YMaps>
                </div>
            </div>
        </div>
    );
};

export default ProductPage;