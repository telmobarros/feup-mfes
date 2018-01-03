
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class TestBuyInPortugal extends MyTestCase {
  public static Manufacturer testManufacturer = new Manufacturer("RENOVA");
  public static Product testProduct =
      new Product(
          "Pocket Tissues",
          "- 3-ply base sheet\n- 36x9 tissues per pack\n- Tissue size: 21x21cm",
          "Health & Personal Care",
          1.23,
          MapUtil.map(
              new Maplet(quotes.WhiteQuote.getInstance(), 0L),
              new Maplet(quotes.BlueQuote.getInstance(), 0L),
              new Maplet(quotes.PinkQuote.getInstance(), 0L),
              new Maplet(quotes.YellowQuote.getInstance(), 0L),
              new Maplet(quotes.OrangeQuote.getInstance(), 0L),
              new Maplet(quotes.PurpleQuote.getInstance(), 0L),
              new Maplet(quotes.GreenQuote.getInstance(), 0L),
              new Maplet(quotes.RedQuote.getInstance(), 0L)));
  public static Client testClient = new Client("logistica@fe.up.pt");

  public static BuyInPortugal configuredBuyInPortugal1() {

    BuyInPortugal bip = new BuyInPortugal();
    bip.setCategories(
        SetUtil.set(
            "Agriculture & Food",
            "Beauty & Health",
            "Books & Audible",
            "Clothes, Shoes & Jewellery",
            "Car & Motorbike",
            "Fresh Products, Drinks & Grocery",
            "Home, Garden, Pets & DIY",
            "Electronics & Computers",
            "Metallurgy, Chemicals, Rubber & Plastics",
            "Movies, TV, Music & Games",
            "Machinery, Industrial Parts & Tools",
            "Toys, Children & Baby",
            "Sports & Outdoors"),
        "");
    bip.addCategory("Real Estate", "");
    bip.setSubcategories(
        MapUtil.map(
            new Maplet("Vanilla Beans", "Agriculture & Food"),
            new Maplet("Plant Seeds & Bulbs", "Agriculture & Food"),
            new Maplet("Nuts & Kernels", "Agriculture & Food"),
            new Maplet("Health & Personal Care", "Beauty & Health"),
            new Maplet("Breads & Bakery", "Fresh Products, Drinks & Grocery"),
            new Maplet("Dairy & Eggs", "Fresh Products, Drinks & Grocery")),
        "");
    bip.addSubcategory("Investment", "Real Estate", "");
    bip.registerManufacturer(testManufacturer.name, "");
    return bip;
  }

  public static BuyInPortugal configuredBuyInPortugal2() {

    BuyInPortugal bip = configuredBuyInPortugal1();
    bip.addProduct(
        testManufacturer.name,
        testProduct.title,
        testProduct.description,
        testProduct.subcategory,
        testProduct.price,
        testProduct.quantities);
    bip.registerClient(testClient.email);
    return bip;
  }

  public static BuyInPortugal configuredBuyInPortugal3() {

    BuyInPortugal bip = configuredBuyInPortugal2();
    bip.addToStock(testManufacturer.name, testProduct.title, quotes.BlueQuote.getInstance(), 36L);
    bip.addToStock(testManufacturer.name, testProduct.title, quotes.WhiteQuote.getInstance(), 2L);
    return bip;
  }

  public static BuyInPortugal configuredBuyInPortugal4() {

    BuyInPortugal bip = configuredBuyInPortugal2();
    bip.registerManufacturer("DANONE", "");
    bip.addProduct(
        "DANONE",
        "YOGURT PURE AROMA TUTTI - FRUTTI 4X120G",
        "",
        "Dairy & Eggs",
        2.45,
        MapUtil.map(new Maplet(quotes.NoneQuote.getInstance(), 0L)));
    bip.addProduct(
        "DANONE",
        "YOGURT CHILD ONE BONGO 8 FRUITS 4X155G",
        "",
        "Dairy & Eggs",
        2L,
        MapUtil.map(new Maplet(quotes.NoneQuote.getInstance(), 0L)));
    bip.addToStock(testManufacturer.name, testProduct.title, quotes.BlueQuote.getInstance(), 36L);
    bip.addToStock(testManufacturer.name, testProduct.title, quotes.WhiteQuote.getInstance(), 2L);
    return bip;
  }

  private static void testSetAdminCode() {

    BuyInPortugal bip = new BuyInPortugal();
    bip.setAdminCode("", "1234");
    assertEqual("1234", bip.adminCode);
  }

  private static void testCategories() {

    BuyInPortugal bip = new BuyInPortugal();
    assertEqual(SetUtil.set(), bip.categories);
    bip.addCategory("Agriculture & Food", "");
    assertTrue(SetUtil.inSet("Agriculture & Food", bip.categories));
    bip.setCategories(SetUtil.set("Beauty & Health", "Books & Audible"), "");
    bip.addCategory("Real Estate", "");
    assertEqual(SetUtil.set("Beauty & Health", "Books & Audible", "Real Estate"), bip.categories);
  }

  private static void testAddSubCategory() {

    BuyInPortugal bip = new BuyInPortugal();
    bip.addCategory("Real Estate", "");
    assertTrue(!(SetUtil.inSet("Investment", MapUtil.dom(bip.subcategories))));
    bip.addSubcategory("Investment", "Real Estate", "");
    assertEqual("Real Estate", ((String) Utils.get(bip.subcategories, "Investment")));
  }

  private static void testRegisterManufacturer() {

    BuyInPortugal bip = new BuyInPortugal();
    assertTrue(!(SetUtil.inSet(testManufacturer.name, MapUtil.dom(bip.manufacturers))));
    bip.registerManufacturer(testManufacturer.name, "");
    assertTrue(SetUtil.inSet(testManufacturer.name, MapUtil.dom(bip.manufacturers)));
  }

  private static void testAddProduct() {

    BuyInPortugal bip = configuredBuyInPortugal1();
    assertTrue(!(SetUtil.inSet(testProduct.title, MapUtil.dom(bip.products))));
    bip.addProduct(
        testManufacturer.name,
        testProduct.title,
        testProduct.description,
        testProduct.subcategory,
        testProduct.price,
        MapUtil.map(new Maplet(quotes.NoneQuote.getInstance(), 0L)));
    assertTrue(SetUtil.inSet(testProduct.title, MapUtil.dom(bip.products)));
    assertEqual(testProduct.title, ((Product) Utils.get(bip.products, testProduct.title)).title);
    assertEqual(
        testProduct.description,
        ((Product) Utils.get(bip.products, testProduct.title)).description);
    assertEqual(
        testProduct.subcategory,
        ((Product) Utils.get(bip.products, testProduct.title)).subcategory);
    assertEqual(testProduct.price, ((Product) Utils.get(bip.products, testProduct.title)).price);
    assertEqual(
        MapUtil.map(new Maplet(quotes.NoneQuote.getInstance(), 0L)),
        ((Product) Utils.get(bip.products, testProduct.title)).quantities);
    assertEqual(
        SetUtil.set(quotes.NoneQuote.getInstance()),
        ((Product) Utils.get(bip.products, testProduct.title)).colors);
  }

  private static void testRegisterClient() {

    BuyInPortugal bip = new BuyInPortugal();
    assertTrue(!(SetUtil.inSet(testClient.email, MapUtil.dom(bip.clients))));
    bip.registerClient(testClient.email);
    assertTrue(SetUtil.inSet(testClient.email, MapUtil.dom(bip.clients)));
  }

  private static void testAddWishList() {

    BuyInPortugal bip = configuredBuyInPortugal2();
    {
      final Client client = ((Client) Utils.get(bip.clients, testClient.email));
      {
        assertTrue(
            !(SetUtil.inSet(
                Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()), client.wishlist)));
        bip.addToWishlist(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
        assertTrue(
            SetUtil.inSet(
                Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()), client.wishlist));
      }
    }
  }

  private static void testRemoveWishList() {

    BuyInPortugal bip = configuredBuyInPortugal2();
    {
      final Client client = ((Client) Utils.get(bip.clients, testClient.email));
      {
        bip.addToWishlist(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
        assertEqual(
            SetUtil.set(Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance())),
            client.wishlist);
        bip.removeFromWishlist(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
        assertTrue(
            !(SetUtil.inSet(
                Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()), client.wishlist)));
      }
    }
  }

  private static void testAddToStock() {

    BuyInPortugal bip = configuredBuyInPortugal2();
    {
      final Product product = ((Product) Utils.get(bip.products, testProduct.title));
      {
        assertTrue(
            Utils.equals(
                ((Number) Utils.get(product.quantities, quotes.BlueQuote.getInstance())), 0L));
        bip.addToStock(
            testManufacturer.name, testProduct.title, quotes.BlueQuote.getInstance(), 36L);
        assertTrue(
            Utils.equals(
                ((Number) Utils.get(product.quantities, quotes.BlueQuote.getInstance())), 36L));
      }
    }
  }

  private static void testRemoveFromStock() {

    BuyInPortugal bip = configuredBuyInPortugal2();
    {
      final Product product = ((Product) Utils.get(bip.products, testProduct.title));
      {
        assertTrue(
            Utils.equals(
                ((Number) Utils.get(product.quantities, quotes.BlueQuote.getInstance())), 0L));
        bip.addToStock(
            testManufacturer.name, testProduct.title, quotes.BlueQuote.getInstance(), 36L);
        bip.removeFromStock(
            testManufacturer.name, testProduct.title, quotes.BlueQuote.getInstance(), 30L);
        assertTrue(
            Utils.equals(
                ((Number) Utils.get(product.quantities, quotes.BlueQuote.getInstance())), 6L));
      }
    }
  }

  private static void testAddToCart() {

    BuyInPortugal bip = configuredBuyInPortugal3();
    {
      final Client client = ((Client) Utils.get(bip.clients, testClient.email));
      {
        assertTrue(
            !(SetUtil.inSet(
                Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()),
                MapUtil.dom(client.cart))));
        bip.addToCart(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
        assertTrue(
            SetUtil.inSet(
                Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()),
                MapUtil.dom(client.cart)));
      }
    }
  }

  private static void testQntAddToCart() {

    BuyInPortugal bip = configuredBuyInPortugal3();
    {
      final Client client = ((Client) Utils.get(bip.clients, testClient.email));
      {
        bip.addToCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance());
        assertTrue(
            Utils.equals(
                ((Number)
                    Utils.get(
                        client.cart, Tuple.mk_(testProduct.title, quotes.BlueQuote.getInstance()))),
                1L));
        bip.setQtyInCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance(), 35L);
        assertTrue(
            Utils.equals(
                ((Number)
                    Utils.get(
                        client.cart, Tuple.mk_(testProduct.title, quotes.BlueQuote.getInstance()))),
                35L));
      }
    }
  }

  private static void testRemoveFromCart() {

    BuyInPortugal bip = configuredBuyInPortugal3();
    {
      final Client client = ((Client) Utils.get(bip.clients, testClient.email));
      {
        bip.addToCart(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
        bip.removeFromCart(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
        assertTrue(
            !(SetUtil.inSet(
                Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()),
                MapUtil.dom(client.cart))));
      }
    }
  }

  private static void testConvertWishList() {

    BuyInPortugal bip = configuredBuyInPortugal3();
    {
      final Client client = ((Client) Utils.get(bip.clients, testClient.email));
      {
        bip.addToWishlist(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
        bip.convertWishlist(testClient.email);
        assertTrue(
            SetUtil.inSet(
                Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()),
                MapUtil.dom(client.cart)));
        assertTrue(
            Utils.equals(
                ((Number)
                    Utils.get(
                        client.cart, Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()))),
                1L));
        assertTrue(
            !(SetUtil.inSet(
                Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()), client.wishlist)));
        bip.setQtyInCart(testClient.email, testProduct.title, quotes.RedQuote.getInstance(), 35L);
        bip.addToWishlist(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
        bip.convertWishlist(testClient.email);
        assertTrue(
            SetUtil.inSet(
                Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()),
                MapUtil.dom(client.cart)));
        assertTrue(
            Utils.equals(
                ((Number)
                    Utils.get(
                        client.cart, Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()))),
                35L));
        assertTrue(
            !(SetUtil.inSet(
                Tuple.mk_(testProduct.title, quotes.RedQuote.getInstance()), client.wishlist)));
      }
    }
  }

  private static void testGetTotalCart() {

    BuyInPortugal bip = configuredBuyInPortugal3();
    bip.addToCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance());
    bip.setQtyInCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance(), 2L);
    assertTrue(Utils.equals(bip.getTotalCart(testClient.email), 2.46));
    bip.addToStock(testManufacturer.name, testProduct.title, quotes.BlueQuote.getInstance(), 1000L);
    bip.setVolumeDiscounts(
        testManufacturer.name,
        testProduct.title,
        MapUtil.map(new Maplet(500L, 1L), new Maplet(750L, 0.5), new Maplet(1000L, 0.25)));
    bip.setQtyInCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance(), 500L);
    assertEqual(500L, bip.getTotalCart(testClient.email));
    bip.setQtyInCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance(), 600L);
    assertEqual(600L, bip.getTotalCart(testClient.email));
    bip.setQtyInCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance(), 800L);
    assertEqual(400L, bip.getTotalCart(testClient.email));
    bip.setQtyInCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance(), 1000L);
    assertEqual(250L, bip.getTotalCart(testClient.email));
  }

  private static void testRemoveProduct() {

    BuyInPortugal bip = configuredBuyInPortugal3();
    bip.addToCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance());
    bip.addToCart(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
    bip.addToWishlist(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
    bip.removeProduct(testManufacturer.name, testProduct.title);
    assertEqual(MapUtil.map(), bip.products);
    assertEqual(
        MapUtil.map(),
        ((Manufacturer) Utils.get(bip.manufacturers, testManufacturer.name)).products);
    assertEqual(SetUtil.set(), ((Client) Utils.get(bip.clients, testClient.email)).wishlist);
    assertEqual(MapUtil.map(), ((Client) Utils.get(bip.clients, testClient.email)).cart);
  }

  private static void testBuy() {

    BuyInPortugal bip = configuredBuyInPortugal3();
    bip.addToCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance());
    bip.setQtyInCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance(), 35L);
    bip.addToCart(testClient.email, testProduct.title, quotes.WhiteQuote.getInstance());
    bip.buy(testClient.email);
    assertEqual(
        SeqUtil.seq(
            MapUtil.map(
                new Maplet(Tuple.mk_(testProduct.title, quotes.BlueQuote.getInstance()), 35L),
                new Maplet(Tuple.mk_(testProduct.title, quotes.WhiteQuote.getInstance()), 1L))),
        ((Client) Utils.get(bip.clients, testClient.email)).buyHistory);
    assertEqual(MapUtil.map(), ((Client) Utils.get(bip.clients, testClient.email)).cart);
    bip.addToCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance());
    bip.buy(testClient.email);
    assertEqual(
        SeqUtil.seq(
            MapUtil.map(
                new Maplet(Tuple.mk_(testProduct.title, quotes.BlueQuote.getInstance()), 1L)),
            MapUtil.map(
                new Maplet(Tuple.mk_(testProduct.title, quotes.BlueQuote.getInstance()), 35L),
                new Maplet(Tuple.mk_(testProduct.title, quotes.WhiteQuote.getInstance()), 1L))),
        ((Client) Utils.get(bip.clients, testClient.email)).buyHistory);
  }

  private static void testSearch() {

    BuyInPortugal bip = configuredBuyInPortugal4();
    {
      final Product product = bip.searchProductsByTitle(testProduct.title);
      {
        assertEqual(testProduct.description, product.description);
      }
    }

    {
      final VDMSet products = bip.searchProductsByCategory("Beauty & Health");
      {
        assertTrue(Utils.equals(products.size(), 1L));
      }
    }

    {
      final VDMSet products = bip.searchProductsBySubcategory("Dairy & Eggs");
      {
        assertTrue(Utils.equals(products.size(), 2L));
      }
    }

    {
      final VDMSet products = bip.searchProductsByManufacturer("DANONE");
      {
        assertTrue(Utils.equals(products.size(), 2L));
      }
    }
  }

  public static void main(String[] args) {

    IO.print("Set admin code: ");
    testSetAdminCode();
    IO.println("Finish");
    IO.print("Add Category: ");
    testCategories();
    IO.println("Finish");
    IO.print("Add SubCategory: ");
    testAddSubCategory();
    IO.println("Finish");
    IO.print("Register Manufacturer: ");
    testRegisterManufacturer();
    IO.println("Finish");
    IO.print("Add Product: ");
    testAddProduct();
    IO.println("Finish");
    IO.print("Register Client: ");
    testRegisterClient();
    IO.println("Finish");
    IO.print("Add to Stock: ");
    testAddToStock();
    IO.println("Finish");
    IO.print("Remove from Stock: ");
    testRemoveFromStock();
    IO.println("Finish");
    IO.print("Add Wish List: ");
    testAddWishList();
    IO.println("Finish");
    IO.print("Add Remove Wish List: ");
    testRemoveWishList();
    IO.println("Finish");
    IO.print("Add To Cart: ");
    testAddToCart();
    IO.println("Finish");
    IO.print("Add Qty To Cart: ");
    testQntAddToCart();
    IO.println("Finish");
    IO.print("Remove from Cart: ");
    testRemoveFromCart();
    IO.println("Finish");
    IO.print("Convert Wish List: ");
    testConvertWishList();
    IO.println("Finish");
    IO.print("Get Total Cart: ");
    testGetTotalCart();
    IO.println("Finish");
    IO.print("Buy: ");
    testBuy();
    IO.println("Finish");
    IO.print("Remove Product: ");
    testRemoveProduct();
    IO.println("Finish");
    IO.print("Search: ");
    testSearch();
    IO.println("Finish");
  }

  public static void testCannotRegisterClient() {

    BuyInPortugal bip = new BuyInPortugal();
    bip.registerClient(testClient.email);
    bip.registerClient(testClient.email);
  }

  public static void testCannotAddProduct() {

    BuyInPortugal bip = configuredBuyInPortugal1();
    bip.addProduct(
        testManufacturer.name,
        testProduct.title,
        testProduct.description,
        testProduct.subcategory,
        testProduct.price,
        MapUtil.map(new Maplet(quotes.NoneQuote.getInstance(), 0L)));
    bip.addProduct(
        testManufacturer.name,
        testProduct.title,
        testProduct.description,
        testProduct.subcategory,
        testProduct.price,
        MapUtil.map(new Maplet(quotes.NoneQuote.getInstance(), 0L)));
  }

  public static void testCannotBuy() {

    BuyInPortugal bip = configuredBuyInPortugal3();
    bip.addToCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance());
    bip.setQtyInCart(testClient.email, testProduct.title, quotes.BlueQuote.getInstance(), 40L);
    bip.buy(testClient.email);
  }

  public static void testCannotAddWishList() {

    BuyInPortugal bip = configuredBuyInPortugal2();
    bip.addToWishlist(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
    bip.addToWishlist(testClient.email, testProduct.title, quotes.RedQuote.getInstance());
  }

  public static void testCannotAddStock() {

    BuyInPortugal bip = configuredBuyInPortugal2();
    bip.addToStock(testManufacturer.name, testProduct.title, quotes.BlueQuote.getInstance(), -36L);
  }

  public TestBuyInPortugal() {}

  public String toString() {

    return "TestBuyInPortugal{"
        + "testManufacturer := "
        + Utils.toString(testManufacturer)
        + ", testProduct := "
        + Utils.toString(testProduct)
        + ", testClient := "
        + Utils.toString(testClient)
        + "}";
  }
}
