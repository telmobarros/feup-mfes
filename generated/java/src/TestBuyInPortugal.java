
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
            new Maplet("Health & Personal Care", "Beauty & Health")),
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
        testProduct.quantities);
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
        testProduct.quantities, ((Product) Utils.get(bip.products, testProduct.title)).quantities);
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
  }

  public static void testAll() {

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
