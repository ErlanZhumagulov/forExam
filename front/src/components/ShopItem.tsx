import React from 'react';
import { Link } from 'react-router-dom';

interface ShopItemProps {
  id: number;
  marketName: string;
  marketDescription: string;
  address: string;
  seller: any;
  products: any[];

}


const ShopItem: React.FC<ShopItemProps> = ({ id, marketName, marketDescription, address}) => {
  return (
    <Link to={`/shop/${id}`}>
      <div>
        <h2>Market Name: {marketName}</h2>
        <h3> Описание магазина: {marketDescription}</h3>
        <h3> Адрес магазина: {address}</h3>

        {/* Здесь вы можете добавить отображение информации о продуктах */}
      </div>
    </Link>
  );
};

export default ShopItem;
