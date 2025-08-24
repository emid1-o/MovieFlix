package br.com.movieflix.service;

import br.com.movieflix.entity.Category;
import br.com.movieflix.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category save(Category category){
       return categoryRepository.save(category);
    }

    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }

    public void deleteById(Long id){
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
        }

    }

    public Category update(Category category, Long id){

        return categoryRepository.findById(id).map(category1 ->{
        category1.setName(category.getName());

        return categoryRepository.save(category1); }).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"musica de id " + id + " não encontrada")
        );
    }
}
