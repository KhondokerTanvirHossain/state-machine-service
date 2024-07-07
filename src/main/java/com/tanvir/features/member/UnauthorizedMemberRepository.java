package com.tanvir.features.member;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UnauthorizedMemberRepository extends ReactiveMongoRepository<UnauthorizedMember, String> {
}
