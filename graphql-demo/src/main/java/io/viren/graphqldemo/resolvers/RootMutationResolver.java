package io.viren.graphqldemo.resolvers;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import io.viren.graphqldemo.domains.AddressDto;
import io.viren.graphqldemo.domains.OrderDto;
import io.viren.graphqldemo.domains.OrderEntryDto;
import io.viren.graphqldemo.domains.PriceDto;
import io.viren.graphqldemo.domains.ProductDto;
import io.viren.graphqldemo.domains.UserDto;
import io.viren.graphqldemo.exceptions.ProductNotFoundException;
import io.viren.graphqldemo.exceptions.UserNotFoundException;
import io.viren.graphqldemo.models.Address;
import io.viren.graphqldemo.models.Order;
import io.viren.graphqldemo.models.OrderEntry;
import io.viren.graphqldemo.models.Price;
import io.viren.graphqldemo.models.Product;
import io.viren.graphqldemo.models.User;
import io.viren.graphqldemo.repositories.OrderRepository;
import io.viren.graphqldemo.repositories.ProductRepository;
import io.viren.graphqldemo.repositories.UserRepository;

@Component
public class RootMutationResolver implements GraphQLMutationResolver {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	public UserDto createOrder(final OrderDto orderDto) {
		final Order order = new Order();
		order.setUser(userRepository.findByUid(orderDto.getUser().getUid())
				.orElseThrow(() -> new UserNotFoundException(orderDto.getUser().getUid())));
		order.setEntries(orderDto.getOrderEntries().stream().map(entry -> this.createEntry(entry, order))
				.collect(Collectors.toList()));

		orderRepository.save(order);

		return userRepository.findByUid(orderDto.getUser().getUid()).map(this::createUser)
				.orElseThrow(() -> new UserNotFoundException(orderDto.getUser().getUid()));
	}

	private OrderEntry createEntry(final OrderEntryDto entryDto, final Order order) {
		final OrderEntry entry = new OrderEntry();
		entry.setProduct(productRepository.findByCode(entryDto.getProduct().getCode())
				.orElseThrow(() -> new ProductNotFoundException(entryDto.getProduct().getCode())));
		entry.setOrder(order);
		entry.setQuantity(entryDto.getQuantity());
		return entry;
	}

	public ProductDto createProduct(final ProductDto productDto) {
		final Product product = createProductInternal(productDto);
		productRepository.save(product);

		return productRepository.findByCode(productDto.getCode()).map(this::createProduct)
				.orElseThrow(() -> new ProductNotFoundException(productDto.getCode()));
	}

	public UserDto createUser(final UserDto userDto) {
		final User user = new User();
		user.setUid(userDto.getUid());
		user.setName(userDto.getName());
		user.setAge(userDto.getAge());
		if (null != userDto.getAddresses()) {

			user.setAddresses(userDto.getAddresses().stream().map(address -> createAddress(address, user))
					.collect(Collectors.toList()));
		}
		userRepository.save(user);
		return userRepository.findByUid(userDto.getUid()).map(this::createUser)
				.orElseThrow(() -> new UserNotFoundException(userDto.getUid()));
	}

	private Address createAddress(final AddressDto addressDto, final User user) {
		final Address address = new Address();
		address.setLine1(addressDto.getLine1());
		address.setLine2(addressDto.getLine2());
		address.setTown(addressDto.getTown());
		address.setCountry(addressDto.getCountry());
		address.setPincode(addressDto.getPincode());
		address.setMobile(addressDto.getMobile());
		address.setUser(user);
		return address;
	}

	private Product createProductInternal(final ProductDto productDto) {
		final Product product = new Product();
		product.setCode(productDto.getCode());
		product.setDescription(productDto.getDescription());
		product.setPrices(null != productDto.getPrices() ? productDto.getPrices().stream()
				.map(price -> this.createPrice(price, product)).collect(Collectors.toList()) : Collections.emptyList());
		return product;
	}

	// updateProductDescription(code:ID!,product:ProductInput):Product!

	public ProductDto updateProductDescription(final String code, final ProductDto productDto) {
		final Product oldProduct = productRepository.findByCode(code)
				.orElseThrow(() -> new ProductNotFoundException(productDto.getCode()));
		productRepository.delete(oldProduct);

		final Product product = createProductInternal(productDto);
		productRepository.save(product);
		return productRepository.findByCode(productDto.getCode()).map(this::createProduct)
				.orElseThrow(() -> new ProductNotFoundException(productDto.getCode()));
	}

	public UserDto updateUser(final String uid, final UserDto userDto) {
		final User oldUser = userRepository.findByUid(uid).orElseThrow(() -> new UserNotFoundException(uid));

		userRepository.delete(oldUser);

		final User user = createUserInternal(userDto);
		userRepository.save(user);

		return userRepository.findByUid(uid).map(this::createUser).orElseThrow(() -> new UserNotFoundException(uid));
	}

	private User createUserInternal(UserDto userDto) {
		final User user = new User();
		user.setUid(userDto.getUid());
		user.setName(userDto.getName());
		user.setAge(userDto.getAge());
		user.setAddresses(userDto.getAddresses().stream().map(address -> this.createAddress(address, user))
				.collect(Collectors.toList()));
		return user;
	}

	private Price createPrice(final PriceDto price, final Product pr) {
		final Price priceModel = new Price();
		priceModel.setValue(price.getValue());
		priceModel.setProduct(pr);
		return priceModel;
	}

	private UserDto createUser(final User user) {
		final UserDto dto = new UserDto();
		dto.setUid(user.getUid());
		dto.setName(user.getName());
		dto.setAge(user.getAge());
		dto.setOrders(user.getOrders().stream().map(this::createOrderDto).collect(Collectors.toList()));

		dto.setAddresses(user.getAddresses().stream().map(this::createAddressDto).collect(Collectors.toList()));
		return dto;
	}

	private OrderDto createOrderDto(final Order order) {
		final OrderDto orderDto = new OrderDto();
		orderDto.setOrderEntries(order.getEntries().stream().map(this::createEntryDto).collect(Collectors.toList()));
		orderDto.setId(order.getId());
		return orderDto;
	}

	private OrderEntryDto createEntryDto(final OrderEntry entry) {

		final OrderEntryDto dto = new OrderEntryDto();
		dto.setProduct(Optional.ofNullable(entry.getProduct()).map(this::createProduct).orElse(null));
		dto.setQuantity(entry.getQuantity());
		dto.setId(entry.getId());
		return dto;
	}

	private AddressDto createAddressDto(final Address address) {
		final AddressDto dto = new AddressDto();
		dto.setLine1(address.getLine1());
		dto.setLine2(address.getLine2());
		dto.setTown(address.getTown());
		dto.setPincode(address.getPincode());
		dto.setCountry(address.getCountry());
		dto.setMobile(address.getMobile());
		return dto;
	}

	private ProductDto createProduct(Product product) {
		final ProductDto pr = new ProductDto();
		pr.setCode(product.getCode());
		pr.setDescription(product.getDescription());
		pr.setPrices(product.getPrices().stream().map(this::createPrice).collect(Collectors.toList()));
		return pr;
	}

	private PriceDto createPrice(Price price) {
		final PriceDto priceDto = new PriceDto();
		priceDto.setValue(price.getValue());
		return priceDto;
	}
}
