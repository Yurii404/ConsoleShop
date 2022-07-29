package ua.issoft.yurii.kupchyn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.entities.Product;
import ua.issoft.yurii.kupchyn.entities.categories.Headphones;
import ua.issoft.yurii.kupchyn.services.sorting.SortProductService;
import ua.issoft.yurii.kupchyn.services.sorting.SortProductServiceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SortProductServiceTest {

    private SortProductService sortProductService;

    @Before
    public void setUp() {
        sortProductService = new SortProductService("normal-sort-config.xml");
    }

    @Test
    public void should_returnSumOfFiveTheMostExpensiveProduct() throws SortProductServiceException {
        int expectSum = 10;

        Category testingCategory = new Headphones();

        testingCategory.addProduct(new Product("Some", "Some", 3));
        testingCategory.addProduct(new Product("Some", "Some", 2));
        testingCategory.addProduct(new Product("Some", "Some", 5));

        int resultSum = sortProductService.sumFiveTheMostExpensiveProduct(testingCategory);

        assertEquals(expectSum, resultSum);
    }

    @Test
    public void should_returnSortedProducts() throws SortProductServiceException {
        List<Product> expectedProductList = new ArrayList<>();
        expectedProductList.add(new Product("Some", "Some", 3));
        expectedProductList.add(new Product("Some", "Some", 2));
        expectedProductList.add(new Product("Some", "Some", 1));

        Category testingCategory = new Headphones();

        testingCategory.addProduct(new Product("Some", "Some", 1));
        testingCategory.addProduct(new Product("Some", "Some", 3));
        testingCategory.addProduct(new Product("Some", "Some", 2));

        List<Product> resultProductList = sortProductService.sortProducts(testingCategory);

        assertArrayEquals(expectedProductList.toArray(), resultProductList.toArray());

    }

    @Test(expected = SortProductServiceException.class)
    public void should_throwException_when_fileNotExist() throws SortProductServiceException {
        new SortProductService("test-name.xml").sortProducts(new Headphones());
    }

    @Test(expected = SortProductServiceException.class)
    public void should_throwException_when_invalidConfigWithNonExistField() throws SortProductServiceException {
        Category testCategory = new Headphones();
        testCategory.addProduct(new Product("Some", "Some", 1));
        testCategory.addProduct(new Product("Some", "Some", 1));

        new SortProductService("config-for-filed-to-sort-products.xml").sortProducts(testCategory);
    }


}
