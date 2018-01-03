
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class BuyInPortugal {
	public VDMSet categories = SetUtil.set();
	public VDMMap subcategories = MapUtil.map();
	public VDMMap manufacturers = MapUtil.map();
	public VDMMap products = MapUtil.map();
	public VDMMap clients = MapUtil.map();
	public String adminCode = SeqUtil.toStr(SeqUtil.seq());

	public void cg_init_BuyInPortugal_1() {

		return;
	}

	public BuyInPortugal() {

		cg_init_BuyInPortugal_1();
	}

	public void setAdminCode(final String curCode, final String nextCode) {

		adminCode = nextCode;
	}

	public void registerManufacturer(final String name, final String code) {

		Manufacturer m = new Manufacturer(name);
		addManufacturer(m);
	}

	private void addManufacturer(final Manufacturer m) {

		manufacturers = MapUtil.munion(Utils.copy(manufacturers), MapUtil.map(new Maplet(m.name, m)));
	}

	public void addCategory(final String category, final String code) {

		categories = SetUtil.union(Utils.copy(categories), SetUtil.set(category));
	}

	public void setCategories(final VDMSet cats, final String code) {

		categories = Utils.copy(cats);
	}

	public void addSubcategory(final String subcategory, final String category, final String code) {

		subcategories =
				MapUtil.munion(Utils.copy(subcategories), MapUtil.map(new Maplet(subcategory, category)));
	}

	public void setSubcategories(final VDMMap subcats, final String code) {

		subcategories = Utils.copy(subcats);
	}

	public void addProduct(
			final String manName,
			final String tit,
			final String des,
			final String cat,
			final Number pr,
			final VDMMap qties) {

		Product product = new Product(tit, des, cat, pr, Utils.copy(qties));
		{
			final Manufacturer manufacturer = ((Manufacturer) Utils.get(manufacturers, manName));
			{
				manufacturer.addProduct(product);
				products = MapUtil.munion(Utils.copy(products), MapUtil.map(new Maplet(tit, product)));
			}
		}

		return;
	}

	public void removeProduct(final String manName, final String title) {

		{
			final Manufacturer manufacturer = ((Manufacturer) Utils.get(manufacturers, manName));
			{
				products = MapUtil.domResBy(SetUtil.set(title), Utils.copy(products));
				manufacturer.removeProduct(title);
				for (Iterator iterator_5 = MapUtil.rng(Utils.copy(clients)).iterator();
						iterator_5.hasNext();
						) {
					Client client = (Client) iterator_5.next();
					for (Iterator iterator_6 = client.wishlist.iterator(); iterator_6.hasNext(); ) {
						Tuple tuplePattern_1 = (Tuple) iterator_6.next();
						Boolean success_1 = tuplePattern_1.compatible(String.class, Object.class);
						String t = null;
						Object color = null;
						if (success_1) {
							t = SeqUtil.toStr(tuplePattern_1.get(0));
							color = ((Object) tuplePattern_1.get(1));
						}

						if (!(success_1)) {
							continue;
						}

						if (Utils.equals(t, title)) {
							client.removeFromWishlist(t, color);
						}
					}
					for (Iterator iterator_7 = MapUtil.dom(client.cart).iterator(); iterator_7.hasNext(); ) {
						Tuple tuplePattern_2 = (Tuple) iterator_7.next();
						Boolean success_2 = tuplePattern_2.compatible(String.class, Object.class);
						String t = null;
						Object color = null;
						if (success_2) {
							t = SeqUtil.toStr(tuplePattern_2.get(0));
							color = ((Object) tuplePattern_2.get(1));
						}

						if (!(success_2)) {
							continue;
						}

						if (Utils.equals(t, title)) {
							client.removeFromCart(t, color);
						}
					}
				}
			}
		}
	}

	public void addToStock(
			final String manName, final String title, final Object color, final Number qty) {

		{
			final Product product = ((Product) Utils.get(products, title));
			{
				product.addToStock(color, qty);
			}
		}
	}

	public void removeFromStock(
			final String manName, final String title, final Object color, final Number qty) {

		{
			final Product product = ((Product) Utils.get(products, title));
			{
				product.removeFromStock(color, qty);
			}
		}
	}

	public void setVolumeDiscounts(final String manName, final String title, final VDMMap volDiscs) {

		{
			final Product product = ((Product) Utils.get(products, title));
			{
				product.setVolumeDiscounts(Utils.copy(volDiscs));
			}
		}
	}

	public void addToWishlist(final String email, final String title, final Object color) {

		{
			final Client client = ((Client) Utils.get(clients, email));
			{
				client.addToWishlist(title, color);
			}
		}
	}

	public void removeFromWishlist(final String email, final String title, final Object color) {

		{
			final Client client = ((Client) Utils.get(clients, email));
			{
				client.removeFromWishlist(title, color);
			}
		}
	}

	public void addToCart(final String email, final String title, final Object color) {

		{
			final Client client = ((Client) Utils.get(clients, email));
			{
				client.addToCart(title, color);
			}
		}
	}

	public void setQtyInCart(
			final String email, final String title, final Object color, final Number qty) {

		{
			final Client client = ((Client) Utils.get(clients, email));
			{
				client.setQtyInCart(title, color, qty);
			}
		}
	}

	public void removeFromCart(final String email, final String title, final Object color) {

		{
			final Client client = ((Client) Utils.get(clients, email));
			{
				client.removeFromCart(title, color);
			}
		}
	}

	public Number getTotalCart(final String email) {

		Number sum = 0L;
		{
			final Client client = ((Client) Utils.get(clients, email));
			final VDMMap cart = client.cart;
			{
				for (Iterator iterator_8 = MapUtil.dom(Utils.copy(cart)).iterator();
						iterator_8.hasNext();
						) {
					Tuple tuplePattern_3 = (Tuple) iterator_8.next();
					Boolean success_3 = tuplePattern_3.compatible(String.class, Object.class);
					String title = null;
					Object color = null;
					if (success_3) {
						title = SeqUtil.toStr(tuplePattern_3.get(0));
						color = ((Object) tuplePattern_3.get(1));
					}

					if (!(success_3)) {
						continue;
					}

					{
						final Number qty = ((Number) Utils.get(cart, Tuple.mk_(title, color)));
						sum =
								sum.doubleValue()
								+ ((Product) Utils.get(products, title)).getPriceWithDiscount(qty).doubleValue()
								* qty.longValue();
					}
				}
				return sum;
			}
		}
	}

	public void buy(final String email) {

		{
			final Client client = ((Client) Utils.get(clients, email));
			final VDMMap cart = client.cart;
			{
				for (Iterator iterator_9 = MapUtil.dom(Utils.copy(cart)).iterator();
						iterator_9.hasNext();
						) {
					Tuple tuplePattern_4 = (Tuple) iterator_9.next();
					Boolean success_4 = tuplePattern_4.compatible(String.class, Object.class);
					String title = null;
					Object color = null;
					if (success_4) {
						title = SeqUtil.toStr(tuplePattern_4.get(0));
						color = ((Object) tuplePattern_4.get(1));
					}

					if (!(success_4)) {
						continue;
					}

					((Product) Utils.get(products, title))
					.removeFromStock(color, ((Number) Utils.get(cart, Tuple.mk_(title, color))));
				}
				client.pushCartToHistory();
			}
		}
	}

	public void convertWishlist(final String email) {

		{
			final Client client = ((Client) Utils.get(clients, email));
			{
				client.convertWishlist();
			}
		}
	}

	public void registerClient(final String email) {

		Client c = new Client(email);
		addClient(c);
	}

	private void addClient(final Client c) {

		clients = MapUtil.munion(Utils.copy(clients), MapUtil.map(new Maplet(c.email, c)));
	}

	public Product searchProductsByTitle(final String title) {

		Product product = null;
		product = ((Product) Utils.get(products, title));
		return product;
	}

	public VDMSet searchProductsByManufacturer(final String man) {

		VDMSet resultPoducts = SetUtil.set();
		resultPoducts = MapUtil.rng(((Manufacturer) Utils.get(manufacturers, man)).products);
		return Utils.copy(resultPoducts);
	}

	public VDMSet searchProductsBySubcategory(final String subcat) {

		VDMSet resultProducts = SetUtil.set();
		for (Iterator iterator_10 = MapUtil.rng(Utils.copy(products)).iterator();
				iterator_10.hasNext();
				) {
			Product product = (Product) iterator_10.next();
			if (Utils.equals(product.subcategory, subcat)) {
				resultProducts = SetUtil.union(Utils.copy(resultProducts), SetUtil.set(product));
			}
		}
		return Utils.copy(resultProducts);
	}

	public VDMSet searchProductsByCategory(final String cat) {

		VDMSet resultProducts = SetUtil.set();
		for (Iterator iterator_11 = MapUtil.rng(Utils.copy(products)).iterator();
				iterator_11.hasNext();
				) {
			Product product = (Product) iterator_11.next();
			if (Utils.equals(((String) Utils.get(subcategories, product.subcategory)), cat)) {
				resultProducts = SetUtil.union(Utils.copy(resultProducts), SetUtil.set(product));
			}
		}
		return Utils.copy(resultProducts);
	}

	public String toString() {

		return "BuyInPortugal{"
				+ "categories := "
				+ Utils.toString(categories)
				+ ", subcategories := "
				+ Utils.toString(subcategories)
				+ ", manufacturers := "
				+ Utils.toString(manufacturers)
				+ ", products := "
				+ Utils.toString(products)
				+ ", clients := "
				+ Utils.toString(clients)
				+ ", adminCode := "
				+ Utils.toString(adminCode)
				+ "}";
	}

	public static void main(String[] args) {

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
		bip.registerManufacturer(TestBuyInPortugal.testManufacturer.name, "");

		bip.addProduct(
				TestBuyInPortugal.testManufacturer.name,
				TestBuyInPortugal.testProduct.title,
				TestBuyInPortugal.testProduct.description,
				TestBuyInPortugal.testProduct.subcategory,
				TestBuyInPortugal.testProduct.price,
				TestBuyInPortugal.testProduct.quantities);
		bip.registerClient(TestBuyInPortugal.testClient.email);
		bip.addToStock(TestBuyInPortugal.testManufacturer.name, TestBuyInPortugal.testProduct.title, quotes.BlueQuote.getInstance(), 36L);
		bip.addToStock(TestBuyInPortugal.testManufacturer.name, TestBuyInPortugal.testProduct.title, quotes.WhiteQuote.getInstance(), 2L);

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
	    bip.addToStock(TestBuyInPortugal.testManufacturer.name, TestBuyInPortugal.testProduct.title, quotes.BlueQuote.getInstance(), 36L);
	    bip.addToStock(TestBuyInPortugal.testManufacturer.name, TestBuyInPortugal.testProduct.title, quotes.WhiteQuote.getInstance(), 2L);
	    IO.println(bip);
	}
}
