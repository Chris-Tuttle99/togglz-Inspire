package com.inspire.togglz2.service;

import com.inspire.togglz2.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService{
    @Override
    public List<Product> getProducts() {
        return Stream.of(new Product(1, "Hamburger", 8.00),
                new Product(2,"Fries",4.50),
                new Product(3, "Soft Drink", 2.50),
                new Product(4, "Ice Cream", 5.75)).collect(Collectors.toList());
    }

}
