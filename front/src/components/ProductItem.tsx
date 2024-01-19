import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getAllImagesForOneProduct } from '../services/getProductImage';
import axios from 'axios';
import Imgix from 'react-imgix';
import ImageComponent from './ImageComponent';
import ParentCriteriaValueComponent from './ParentCriteriaValueComponent';




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





const ProductItem: React.FC<ProductItemProps> = ({ id, nameProduct, price, description, availability, idImages,
    additionalSettingsCriteriaList, additionalSettingsValueList, categoriesList }) => {



    return (
        <Link to={`/product/${id}`}>
            <div style={{
                border: '1px solid black',
                padding: '10px',
                margin: '20px'
            }}>
                <h2>Product Name: {nameProduct}</h2>
                <h3> Цена : {price}</h3>
                <h3> Описание: {description}</h3>
                <h3> Наличие: {availability}</h3>
                <h3> id первого изображения: {idImages[0]}</h3>
                <ParentCriteriaValueComponent
                    criteria={additionalSettingsCriteriaList}
                    values={additionalSettingsValueList}
                />
                <div>
                    {categoriesList.map((item, index) => (
                        <div key={index}>{item}</div>
                    ))}
                    <div>
                        <ImageComponent imageId={idImages[0].toString()} />

                    </div>


                </div>
            </div>

        </Link >
    );
};

export default ProductItem;
