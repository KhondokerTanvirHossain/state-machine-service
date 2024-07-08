package com.tanvir.features.memberv2.unauthmember;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UnauthMemberRepository extends ReactiveMongoRepository<UnauthMember, String> {
}
