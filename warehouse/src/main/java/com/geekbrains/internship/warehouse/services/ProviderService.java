package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.Provider;

import java.util.List;

public interface ProviderService {

        /**
         * Создает нового поставщика
         * @param provider - поставщик для создания
         */
        Provider create(Provider provider);

        /**
         * Возвращает список всех имеющихся поставщиков
         * @return список поставщиков
         */
        List<Provider> readAll();

        /**
         * Возвращает поставщика по его ID
         * @param id - ID поставщика
         * @return - объект поставщика с заданным ID
         */
        Provider read(Long id);

        /**
         * Обновляет поставщика с заданным ID,
         * в соответствии с переданным поставщиком
         * @param provider - gjcnfdobr в соответсвии с которым нужно обновить данные
         * @param id - id поставщика которого нужно обновить
         * @return - true если данные были обновлены, иначе false
         */
        boolean update(Provider provider, Long id);

        /**
         * Удаляет поставщика с заданным ID
         * @param id - id поставщика, которого нужно удалить
         * @return - true если поставщик был удален, иначе false
         */
        boolean delete(Long id);

}
