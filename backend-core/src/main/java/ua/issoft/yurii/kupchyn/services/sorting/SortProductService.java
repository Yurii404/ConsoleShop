package ua.issoft.yurii.kupchyn.services.sorting;

import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.entities.CategoryCloneException;
import ua.issoft.yurii.kupchyn.entities.Product;
import ua.issoft.yurii.kupchyn.services.sorting.comparators.ProductComparator;
import ua.issoft.yurii.kupchyn.services.sorting.comparators.ProductComparatorException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortProductService {
    private final String nameSortConfigFile;
    private Comparator<Product> comparator;


    public SortProductService(String nameSortConfigFile) {
        this.nameSortConfigFile = nameSortConfigFile;
    }

    public List<Product> sortProducts(Category category) throws SortProductServiceException {
        if (comparator == null) {
            comparator = initComparator();
        }

        try {
            List<Product> products = category.clone().getProducts();

            products.sort(comparator);

            return products;
        } catch (ProductComparatorException | CategoryCloneException e) {
            throw new SortProductServiceException("Failed to sort products", e);
        }

    }

    public int sumFiveTheMostExpensiveProduct(Category category) throws SortProductServiceException {
        try {
            int sumOfFiveTheMostExpensiveProduct;
            List<Product> workProductList = category.clone().getProducts();

            workProductList.sort(new ProductComparator("price", "desk"));

            sumOfFiveTheMostExpensiveProduct = workProductList.stream()
                    .limit(5)
                    .map(Product::getPrice)
                    .mapToInt(Integer::intValue)
                    .sum();

            return sumOfFiveTheMostExpensiveProduct;

        } catch (CategoryCloneException | ProductComparatorException e) {
            throw new SortProductServiceException("Failed to sum products", e);
        }
    }

    private Comparator<Product> initComparator() throws SortProductServiceException {
        List<Comparator<Product>> comparators = new ArrayList<>();
        List<SortCriteria> criteriaList;
        SortCriteriaXmlParser xmlParser;
        Comparator<Product> combinedComparator;

        try {
            xmlParser = new SortCriteriaXmlParser(nameSortConfigFile);
            criteriaList = xmlParser.getCriteria();
        } catch (XMLParserException e) {
            throw new SortProductServiceException("Failed to parse xml config", e);
        }

        for (SortCriteria criteria : criteriaList) {
            String fieldName = criteria.getFieldName();
            String order = criteria.getOrder();
            comparators.add(new ProductComparator(fieldName, order));
        }

        try {
            combinedComparator = comparators.stream()
                    .reduce(Comparator::thenComparing)
                    .orElseThrow(() -> {
                        throw new ProductComparatorException("Failed to combine comparators");
                    });
        } catch (ProductComparatorException e) {
            throw new SortProductServiceException("Failed to init comparator", e);
        }

        return combinedComparator;
    }
}
