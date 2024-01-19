import React, { useEffect, useState } from 'react'
import { getToken } from '../services/demo'
import MyMap from '../components/MapComponent'
import YMapsMap from '@pbe/react-yandex-maps/typings/Map'
import { YMaps, Map } from '@pbe/react-yandex-maps'
import MapComponent from '../components/MapComponent'

import axios from 'axios';
import { getTokenFromLocalStorage } from '../services/token'
import ProductItem from '../components/ProductItem'



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


interface AdditionalSettingsRequest {
  criteria: string;
  criteriaValue: string;
}


const ClientPage = () => {
  const [marketName, setMarketName] = useState('');
  const [category, setCategory] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [search, setSearch] = useState('');
  const [additionalSettings, setAdditionalSettings] = useState<AdditionalSettingsRequest[]>([]);
  const [allProducts, setAllProducts] = useState<Product[]>([]);



  const handleAddParameter = () => {
    setAdditionalSettings([...additionalSettings, { criteria: '', criteriaValue: '' }]);
  };


  useEffect(() => {
    const filterProductRequest = {
      marketId: '',
      marketName,
      category,
      maxPrice: parseInt(maxPrice),
      search,
      additionalSettings,
    };
    const fetchData = async () => {
      try {
        const response = await axios.post('http://localhost:8081/api/v1/page-product/use_filter', filterProductRequest, {
          headers: {
            Authorization: getTokenFromLocalStorage(), // Замените на фактический токен
          },
        });
        setAllProducts(response.data);
      } catch (error) {
        console.log('Error fetching markets:', error);
      }
    };

    fetchData();
  }, []);

  const sendFilterRequest = async (event: any) => {
    event.preventDefault();

    const filterProductRequest = {
      marketId: '',
      marketName,
      category,
      maxPrice: parseInt(maxPrice),
      search,
      additionalSettings,
    };



    try {
      const response = await axios.post('http://localhost:8081/api/v1/page-product/use_filter', filterProductRequest, {
        headers: {
          Authorization: getTokenFromLocalStorage(),
        },
      });
      setAllProducts(response.data);

      console.log(response)


    } catch (error) {
      console.error('Error filtering products:', error);
    }
  };

  const handleCategoryChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setCategory(event.target.value);
  };



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
      <form onSubmit={sendFilterRequest}>

        <br />
        <label>
          Название магазина:
          <input type="text" value={marketName} onChange={(e) => setMarketName(e.target.value)} />
        </label>
        <br />
        <label>
          Category:
          <label>
            Category:
            <select value={category} onChange={handleCategoryChange}>
              <option value="">Выберите категорию</option>
              <option value="CATEGORY1">CATEGORY1</option>
              <option value="CATEGORY2">CATEGORY2</option>
              <option value="CATEGORY3">CATEGORY3</option>
            </select>
          </label>
        </label>
        <br />
        <label>
          Максимальная цена:
          <input type="number" value={maxPrice} onChange={(e) => setMaxPrice(e.target.value)} />
        </label>
        <br />
        <label>
          Поиск товара:
          <input type="text" value={search} onChange={(e) => setSearch(e.target.value)} />
        </label>
        <br />
        <h3>Дополнительные параметры:</h3>


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

        <button onClick={handleAddParameter}>Добавить параметр</button>
        <br />
        <button onClick={sendFilterRequest}>Фильтрация запросов</button>
      </form>

      {allProducts.map((product) => (
        <ProductItem
          key={product.id}
          id={product.id}
          nameProduct={product.nameProduct}
          price={product.price}
          availability={product.availability}
          idImages={product.idImages}
          description={product.description}

          additionalSettingsCriteriaList={product.additionalSettingsCriteriaList}
          additionalSettingsValueList={product.additionalSettingsValueList}
          categoriesList={product.categoriesList}

        />
      ))}
    </div>
  );
};

export default ClientPage;
