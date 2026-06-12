package com.towhid.spring_data.day07.jpa.service;

import com.towhid.spring_data.day07.jpa.entity.Category;
import com.towhid.spring_data.day07.jpa.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(String name,String description){
        if(categoryRepository.existsByName(name)){
            throw new RuntimeException("Category already exists: " + name);
        }
        Category category = new Category(name,description);
        Category saved = categoryRepository.save(category);
        System.out.println("Category created with ID: " + saved.getId());
        return saved;
    }

    public Category getById(Integer id){
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                         new RuntimeException("Category not exists with id: " + id));
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public Category getByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found with name: "
                                        + name));
    }

    public List<Category> searchByKeyword(String keyword){
        return categoryRepository.findByNameContaining(keyword);
    }

    public Category updateCategory(Integer id,String name,
                                   String description){
        Category existing = getById(id);
        existing.setName(name);
        existing.setDescription(description);
        Category updated = categoryRepository.save(existing);
        System.out.println(" Category updated: " + id);
        return updated;
    }

    public void deleteCategory(Integer id){
        getById(id);
        categoryRepository.deleteById(id);
        System.out.println(" Category deleted: " + id);
    }
}
