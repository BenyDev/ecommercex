package com.s23358.ecommercex;

import com.s23358.ecommercex.address.Address;
import com.s23358.ecommercex.admin.Admin;
import com.s23358.ecommercex.brand.entity.Brand;
import com.s23358.ecommercex.brand.repository.BrandRepository;
import com.s23358.ecommercex.category.entity.Category;
import com.s23358.ecommercex.category.repository.CategoryRepository;
import com.s23358.ecommercex.customer.entity.Customer;
import com.s23358.ecommercex.enums.AccountStatus;
import com.s23358.ecommercex.enums.Unit;
import com.s23358.ecommercex.person.entity.Person;
import com.s23358.ecommercex.person.repository.PersonRepository;
import com.s23358.ecommercex.product.entity.Product;
import com.s23358.ecommercex.product.repository.ProductRepository;
import com.s23358.ecommercex.role.entity.Role;
import com.s23358.ecommercex.role.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class EcommercexApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommercexApplication.class, args);
	}

	@Bean
	CommandLineRunner seedData(
			BrandRepository brandRepository,
			CategoryRepository categoryRepository,
			ProductRepository productRepository,
			RoleRepository roleRepository
	) {
		return new CommandLineRunner() {
			@Override
			@Transactional
			public void run(String... args) {

				boolean isAdmin = roleRepository.findByName("ADMIN").isPresent();
				boolean isCustomer = roleRepository.findByName("CUSTOMER").isPresent();

				if(!isAdmin){
					Role admin = Role.builder().name("ADMIN").build();
					roleRepository.save(admin);
				}

				if(!isCustomer){
					Role customer = Role.builder().name("CUSTOMER").build();
					roleRepository.save(customer);
				}


				List<Brand> brands = List.of(
						Brand.builder().name("Nike").shortName("NIKE").description("Sportswear brand").logo("/uploads/brands/placeholder.png").build(),
						Brand.builder().name("Adidas").shortName("ADID").description("Sportswear brand").logo("/uploads/brands/placeholder.png").build(),
						Brand.builder().name("Puma").shortName("PUMA").description("Sportswear brand").logo("/uploads/brands/placeholder.png").build(),
						Brand.builder().name("Reebok").shortName("REEB").description("Sportswear brand").logo("/uploads/brands/placeholder.png").build(),
						Brand.builder().name("New Balance").shortName("NB").description("Sportswear brand").logo("/uploads/brands/placeholder.png").build(),
						Brand.builder().name("Asics").shortName("ASICS").description("Sportswear brand").logo("/uploads/brands/placeholder.png").build()
				);

				for (Brand b : brands) {
					if (!brandRepository.existsByName(b.getName())) {
						brandRepository.save(b);
					}
				}


				Brand nike = brandRepository.findByName("Nike").orElseThrow();
				Brand adidas = brandRepository.findByName("Adidas").orElseThrow();
				Brand puma = brandRepository.findByName("Puma").orElseThrow();
				Brand reebok = brandRepository.findByName("Reebok").orElseThrow();
				Brand nb = brandRepository.findByName("New Balance").orElseThrow();
				Brand asics = brandRepository.findByName("Asics").orElseThrow();
				List<Brand> brandPool = List.of(nike, adidas, puma, reebok, nb, asics);


				List<String> categoryNames = List.of(
						"Shoes",
						"T-Shirts",
						"Hoodies",
						"Accessories",
						"Equipment"
				);

				for (String name : categoryNames) {
					if (!categoryRepository.existsByName(name)) {
						categoryRepository.save(Category.builder().name(name).build());
					}
				}


				List<Category> categories = categoryNames.stream()
						.map(n -> categoryRepository.findByName(n).orElseThrow())
						.toList();

				int brandIndex = 0;
				for (Category c : categories) {


					for (int i = 1; i <= 3; i++) {
						String productName = c.getName() + " Product " + i;

						if (productRepository.existsByNameAndBelongsToCategory_Id(productName, c.getId())) {
							continue;
						}

						Brand brand = brandPool.get(brandIndex % brandPool.size());
						brandIndex++;

						Product p = Product.builder()
								.name(productName)
								.price(new BigDecimal("199.99").add(new BigDecimal(i)))
								.description("Seed product for category: " + c.getName())
								.unit(Unit.PIECE)
								.stockQuantity(10 * i)
								.weight(new BigDecimal("0.80"))
								.images(List.of())
								.isActive(true)
								.brand(brand)
								.belongsToCategory(c)
								.build();

						productRepository.save(p);
					}
				}
			}
		};
	}

	@Bean
	CommandLineRunner seedUsers(
			RoleRepository roleRepository,
			PersonRepository personRepository,
			PasswordEncoder passwordEncoder
	) {
		return args -> {


			Role adminRole = roleRepository.findByName("ADMIN")
					.orElseGet(() -> roleRepository.save(
							Role.builder().name("ADMIN").build()
					));

			Role customerRole = roleRepository.findByName("CUSTOMER")
					.orElseGet(() -> roleRepository.save(
							Role.builder().name("CUSTOMER").build()
					));


			String adminEmail = "admin@ecommercex.com";

			if (personRepository.findByHasCustomer_Email(adminEmail).isEmpty()) {

				Admin adminProfile = Admin.builder()
						.firstName("System")
						.lastName("Admin")
						.email(adminEmail)
						.password(passwordEncoder.encode("Admin123!"))
						.phoneNumber("123456789")
						.status(AccountStatus.ACTIVE)
						.isActive(true)
						.lastLoginAt(LocalDateTime.now())
						.build();


				Address adminAddress = Address.builder()
						.street("Admin Street")
						.streetNum("1")
						.localNum("1")
						.city("Warsaw")
						.zipCode("00-001")
						.country("Poland")
						.isDefault(true)
						.build();

				adminProfile.addAddress(adminAddress);

				Person admin = Person.builder()
						.hasCustomer(adminProfile)
						.roles(List.of(adminRole,customerRole))
						.build();

				personRepository.save(admin);
			}


			String customerEmail = "customer@ecommercex.com";

			if (personRepository.findByHasCustomer_Email(customerEmail).isEmpty()) {

				Customer customerProfile = Customer.builder()
						.firstName("John")
						.lastName("Customer")
						.email(customerEmail)
						.password(passwordEncoder.encode("Customer123!"))
						.phoneNumber("987654321")
						.status(AccountStatus.ACTIVE)
						.build();

				Address customerAddress = Address.builder()
						.street("Customer Street")
						.streetNum("10")
						.localNum("5")
						.city("Krakow")
						.zipCode("30-001")
						.country("Poland")
						.isDefault(true)
						.build();

				customerProfile.addAddress(customerAddress);

				Person customer = Person.builder()
						.hasCustomer(customerProfile)
						.roles(List.of(customerRole))
						.build();

				personRepository.save(customer);
			}
		};
	}



}
