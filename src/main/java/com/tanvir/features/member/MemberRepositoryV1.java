package com.tanvir.features.member;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MemberRepositoryV1 extends ReactiveMongoRepository<Member, String> {
}
