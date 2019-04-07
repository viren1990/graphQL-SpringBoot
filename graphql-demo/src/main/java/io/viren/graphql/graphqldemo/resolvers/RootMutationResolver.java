package io.viren.graphql.graphqldemo.resolvers;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import io.viren.graphql.graphqldemo.domains.AddressDto;
import io.viren.graphql.graphqldemo.domains.PriceDto;
import io.viren.graphql.graphqldemo.domains.ProductDto;
import io.viren.graphql.graphqldemo.domains.UserDto;
import io.viren.graphql.graphqldemo.exceptions.ProductNotFoundException;
import io.viren.graphql.graphqldemo.exceptions.UserNotFoundException;
import io.viren.graphql.graphqldemo.models.Address;
import io.viren.graphql.graphqldemo.models.Price;
import io.viren.graphql.graphqldemo.models.Product;
import io.viren.graphql.graphqldemo.models.User;
import io.viren.graphql.graphqldemo.repositories.ProductRepository;
import io.viren.graphql.graphqldemo.repositories.UserRepository;

@Component
public class RootMutationResolver implements GraphQLMutationResolver {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

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

	private UserDto createUser(final User user) {
		final UserDto dto = new UserDto();
		dto.setUid(user.getUid());
		dto.setName(user.getName());
		dto.setAge(user.getAge());
		return dto;
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

	private Price createPrice(final PriceDto price, final Product pr) {
		final Price priceModel = new Price();
		priceModel.setValue(price.getValue());
		priceModel.setProduct(pr);
		return priceModel;
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
