///*
// * Copyright 2017 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.springframework.hateoas.examples;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
///**
// * Pre-load some data using a Spring Boot {@link CommandLineRunner}.
// *
// * @author Greg Turnquist
// */
//@Component
//class DatabaseLoader {
//
//	/**
//	 * Use Spring to inject a {@link PlayerRepository} that can then load data.
//	 * Since this will run only after the app is operational, the database will be
//	 * up.
//	 *
//	 * @param repository
//	 */
//	@Bean
//	CommandLineRunner init(PlayerRepository repository) {
//
//		return args -> {
//			repository.save(new Player("久余", "", "0"));
//			repository.save(new Player("爸点爸", "", "0"));
//			repository.save(new Player("久原", "", "0"));
//			repository.save(new Player("久羽", "", "0"));
//			repository.save(new Player("久鹏", "", "0"));
//			repository.save(new Player("久伯", "", "0"));
//			repository.save(new Player("小赖", "", "0"));
//			repository.save(new Player("闻歌", "", "0"));
//		};
//	}
//
//}
