package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.Category;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAllCategories();
}
