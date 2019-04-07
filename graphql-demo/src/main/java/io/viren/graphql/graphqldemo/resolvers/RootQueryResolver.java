package io.viren.graphql.graphqldemo.resolvers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import io.viren.graphql.graphqldemo.domains.AddressDto;
import io.viren.graphql.graphqldemo.domains.OrderDto;
import io.viren.graphql.graphqldemo.domains.OrderEntryDto;
import io.viren.graphql.graphqldemo.domains.PriceDto;
import io.viren.graphql.graphqldemo.domains.ProductDto;
import io.viren.graphql.graphqldemo.domains.UserDto;
import io.viren.graphql.graphqldemo.exceptions.OrderNotFoundException;
import io.viren.graphql.graphqldemo.exceptions.ProductNotFoundException;
import io.viren.graphql.graphqldemo.models.Address;
import io.viren.graphql.graphqldemo.models.Order;
import io.viren.graphql.graphqldemo.models.OrderEntry;
import io.viren.graphql.graphqldemo.models.Price;
import io.viren.graphql.graphqldemo.models.Product;
import io.viren.graphql.graphqldemo.models.User;
import io.viren.graphql.graphqldemo.repositories.OrderRepository;
import io.viren.graphql.graphqldemo.repositories.ProductRepository;
import io.viren.graphql.graphqldemo.repositories.UserRepository;

@Component
public class RootQueryResolver implements GraphQLQueryResolver {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	public List<OrderDto> getAllOrders() {
		return StreamSupport.stream(orderRepository.findAll().spliterator(), false).map(this::createOrderDto)
				.collect(Collectors.toList());
	}

	public OrderDto getOrderById(final String orderId) {
		return orderRepository.findById(Long.parseLong(orderId)).map(this::createOrderDto)
				.orElseThrow(() -> new OrderNotFoundException(orderId));
	}

	public List<ProductDto> getAllProducts() {
		return StreamSupport.stream(productRepository.findAll().spliterator(), false).map(this::createProduct)
				.collect(Collectors.toList());
	}

	public ProductDto productByCode(final String code) {
		return productRepository.findByCode(code).map(this::createProduct)
				.orElseThrow(() -> new ProductNotFoundException(code));
	}

	public List<UserDto> getAllUsers() {
		return StreamSupport.stream(userRepository.findAll().spliterator(), false).map(this::createUserDto)
				.collect(Collectors.toList());
	}

	private UserDto createUserDto(final User user) {
		final UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setAge(user.getAge());
		dto.setOrders(user.getOrders().stream().map(this::createOrderDto).collect(Collectors.toList()));
		dto.setAddresses(user.getAddresses().stream().map(this::createAddressDto).collect(Collectors.toList()));
		return dto;
	}

	private AddressDto createAddressDto(final Address address) {
		final AddressDto dto = new AddressDto();
		dto.setLine1(address.getLine1());
		dto.setLine2(address.getLine2());
		dto.setCountry(address.getCountry());
		dto.setPincode(address.getPincode());
		dto.setTown(address.getPincode());
		dto.setMobile(address.getMobile());
		return dto;
	}
	
	private OrderDto createOrderDto(final Order order) {
		final OrderDto orderDto = new OrderDto();
		orderDto.setOrderEntries(order.getEntries().stream().map(this::createEntryDto).collect(Collectors.toList()));
		return orderDto;
	}

	private OrderEntryDto createEntryDto(final OrderEntry entry) {
		final OrderEntryDto dto = new OrderEntryDto();
		dto.setId(entry.getId());
		dto.setProduct(createProduct(entry.getProduct()));
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
